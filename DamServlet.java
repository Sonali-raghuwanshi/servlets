package com.pwc.madison.core.servlets;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.jcr.Node;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/myservlet",
                "sling.servlet.extensions=html",
                "sling.servlet.selector=dim",

        }


)
public class DamServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws ServletException, IOException {
        try {

            String path = req.getParameter("path");
            Resource resource;

            ResourceResolver resourceResolver = req.getResourceResolver();
            resourceResolver.adaptTo(Resource.class);

            Resource resource1 = resourceResolver.getResource(path);
            List<String> list = new ArrayList<String>();


            allChilds(resource1, list, resp);
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                if (it.next() != null) {
//                    resp.getWriter().write("path is:- "+it.next()+"\n");
                    Node node = req.getResourceResolver().getResource(it.next() + "/jcr:content/renditions/original/jcr:content").adaptTo(Node.class);
                    if (node != null) {
                        try {
                            InputStream in = node.getProperty("jcr:data").getBinary().getStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                            String line;
                            int flag = 0;
                            while ((line = reader.readLine()) != null) {
                                int n = line.length();
                                for (int i = 0; i < n - 6; i++) {
                                    String temp = line.substring(i, 7 + i), temp2 = "<li><p>";
                                    if (temp.equals(temp2)) {
//                                        for (int j = i; j < n - 2; j++) {
//                                            String temp1 = line.substring(j, 3 + j), temp3 = "<p>";
//                                            if (temp1.equals(temp3)) {
                                                resp.getWriter().write("LI-P tag is available" + it.next() + "\n");
                                                flag = 1;
                                                break;
//                                            }
//                                        }

//                                        if (flag == 1)
//                                            break;
                                    }
                                    if (flag == 1)
                                        break;
                                }
                            }
                            if (flag == 0)
                                resp.getWriter().write("There is no li-p tag in this file "+it.next()+"\n");
                            reader.close();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            resp.getWriter().write("ERROR : Not able to read, something is wrong");
                            e.printStackTrace();
                        }
                    } else {
                        resp.getWriter().write("File Not Found!");
                    }
                } else {
                    resp.getWriter().write("Please provide file path in page quesry string parameter e.g. ?file=/content/mysite/mypage");
                }
                resp.setContentType("text/plain");
            }
        }
       catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        private static void allChilds(Resource r1, List<String> list, SlingHttpServletResponse resp) throws IOException {
        if(r1 != ObjectUtils.NULL){
            Iterator<Resource> child =  r1.listChildren();
            if(child!= ObjectUtils.NULL){
                for (Iterator<Resource> it = child; it.hasNext(); ) {
                    Resource r = it.next();
                    allChilds(r, list, resp);
                    String extension = FilenameUtils.getExtension(r.getName());
//                    resp.getWriter().write(extension);
                    if(extension.equals("dita") || extension.equals("xml")) {
                        list.add(r.getPath());
//                        resp.getWriter().write(r.getPath()+"\n");
                    }
                }
            }
        }
    }
}

    
