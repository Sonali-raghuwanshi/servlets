package com.mysite.core.servlets;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//import org.json.simple.parser.JSONParser;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import static org.apache.sling.api.servlets.ServletResolverConstants.*;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_SELECTORS;

@Component(service = Servlet.class, property = {
        SLING_SERVLET_PATHS+ "=/bin/myservlet",
        SLING_SERVLET_METHODS + "=GET",
        SLING_SERVLET_EXTENSIONS + "=in",
        SLING_SERVLET_SELECTORS + "=cn",
}
)
    public class ContentFragment extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

    protected static final Logger LOGGER = LoggerFactory.getLogger(ContentFragment.class);

    @Reference
    ResourceResolverFactory resolverFactory;



    private static final String USER_AGENT = "Mozilla/5.0";

    @Override
    protected void doGet(SlingHttpServletRequest request,
                         SlingHttpServletResponse response) throws ServletException,
            IOException {
        String country = request.getParameter("country"); //it is for query parameter
//        response.getWriter().write(country);
        Resource original;
//        private static final String USER_AGENT = "Mozilla/5.0";
        String myJSON = "";


        LOGGER.info("before factory..");
        HashMap<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, "systemUser");
        LOGGER.info("After factory..");

        ResourceResolver resolver;
        try {
            resolver = resolverFactory.getServiceResourceResolver(param);
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
        JSONObject json = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet getRequest = new HttpGet("http://localhost:4502/api/assets/helpline-portal-page.model.json");
        final String auth = "admin" + ":" + "admin";
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        getRequest.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
//        getRequest.addHeader("User-Agent", USER_AGENT);
//        getRequest.addHeader("accept", "application/json");
//        getRequest.addHeader("ClientId", "123456");
//        getRequest.addHeader("Request-Tracking-Id", "123456");
        HttpResponse httpResponse = httpClient.execute(getRequest);
        LOGGER.info("before if condition");
        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            LOGGER.info("inside if condition");
            throw new RuntimeException("Failed : HTTP error code : "
                    + httpResponse.getStatusLine().getStatusCode());

        } else {
            StringBuilder sb = new StringBuilder();
            LOGGER.info("before buffer reader");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (httpResponse.getEntity().getContent())));
            String output;
            while ((output = br.readLine()) != null) {
                myJSON = myJSON + output;
                sb.append(output);
            }
//            response.getWriter().write(myJSON);
            InputStream is = new ByteArrayInputStream(myJSON.getBytes()); //we are sending the JSON data as a String.
            AssetManager assetMgr = resolver.adaptTo(AssetManager.class);
            assetMgr.createAsset("/api/assets/helpline-portal-page.json", is, "application/json", true);
            try {
                json = new JSONObject(sb.toString());
            } catch (JSONException e) {
                LOGGER.info("EXCEPTION");
            }
        }
        response.getWriter().println(json);
//        JSONParser parser = new JSONParser();
//        JSONObject jsoN = (JSONObject) parser.parse();


    }



}
