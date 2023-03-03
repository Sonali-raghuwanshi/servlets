package com.pwc.madison.core.servlets;


import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;

@Component(
        immediate = true,
        service = Servlet.class,
        property = {
                "sling.servlet.resourceTypes=sling/servlet/default",
                "sling.servlet.extensions=html",
                "sling.servlet.selector=pin",
        }

)
public class Task1 extends SlingSafeMethodsServlet {




    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException{

        Resource resource;
        //response.getWriter().write("success");
        String pathx = request.getRequestURI();
        String path;
        path = StringUtils.substringBefore(pathx,".") + "/jcr:content/root";

        //response.getWriter().write(path);

        ResourceResolver resourceResolver = request.getResourceResolver();
        resourceResolver.adaptTo(Resource.class);

        Resource resource1 = resourceResolver.getResource(path);
        List<Resource> list = new ArrayList<Resource>();

        Iterator<Resource> it = resource1.listChildren();
        //response.getWriter().write(request.getRequestURI());
        allChilds(resource1,list,response);
        response.getWriter().write(path);

    }
    static void allChilds(Resource r1, List<Resource> list, SlingHttpServletResponse resp) throws IOException {
        if(r1 != ObjectUtils.NULL){
            Iterator<Resource> child =  r1.listChildren();
            if(child!= ObjectUtils.NULL){
                for (Iterator<Resource> it = child; it.hasNext(); ) {
                    Resource r = it.next();
                    allChilds(r, list, resp);
                    if(r.getResourceType().toString().startsWith("pwc-madison")){
                    resp.getWriter().write(r.getName()+"\n");
                    }
                }
            }
        }
    }
}
