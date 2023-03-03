package com.pwc.madison.core.servlets;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import javax.servlet.Servlet;
import java.util.HashMap;
import java.util.Map;

import static com.day.cq.dam.api.handler.AssetHandler.log;

@Component(
        immediate = true,
        service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/myservlet/testing"
        }

)
public class testingServlet extends SlingSafeMethodsServlet {


    /**
     * Injecting the QueryBuilder dependency
     */
    @Reference
    private QueryBuilder builder;

    /**
     * Session object
     */
    private Session session;

    /**
     * Overridden doGet() method which executes on HTTP GET request
     */
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {

        try {

            log.info("----------< Executing Query Builder Servlet >----------");

            /**
             * This parameter is passed in the HTTP call
             */
            String param = request.getParameter("param");

            log.info("Search term is: {}", param);

            /**
             * Get resource resolver instance
             */
            ResourceResolver resourceResolver = request.getResourceResolver();

            /**
             * Adapting the resource resolver to the session object
             */
            session = resourceResolver.adaptTo(Session.class);

            /**
             * Map for the predicates
             */
            Map<String, String> predicate = new HashMap<>();

            /**
             * Configuring the Map for the predicate
             */
            predicate.put("path", "/content/madison-ums/en");
            predicate.put("type", "cq:PageContent");
            predicate.put("property", "jcr:content/cq:Page");

            /**
             * Creating the Query instance
             */
            Query query;
            query = builder.createQuery(PredicateGroup.create(predicate), session);

            query.setStart(0);
            query.setHitsPerPage(20);

            /**
             * Getting the search results
             */
            SearchResult searchResult = query.getResult();

            for (Hit hit : searchResult.getHits()) {

                String path = hit.getPath();

                response.getWriter().println(path);
            }
        } catch (Exception e) {

            log.error(e.getMessage(), e);
        } finally {

            if (session != null) {

                session.logout();
            }

        }

    }
}
