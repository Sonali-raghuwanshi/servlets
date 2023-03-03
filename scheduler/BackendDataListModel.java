package com.pwc.madison.core.models;



import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.*;

//@Model(adaptables = {Resource.class},resourceType = "pwc-madison/components/list-backend-data",defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Model(adaptables=Resource.class)
public class BackendDataListModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackendDataListModel.class);

    @Inject @Default(values="/content/pwc-madison/us/en")
    private String path;

    @Inject @Default(values = "new-tag:java")
    private List<String> tags;
    @Inject @Default(values = "OR")
    private String presets;

    @SlingObject
    private ResourceResolver resourceResolver;


    @SlingObject
    private Resource resource;



    @OSGiService
    private QueryBuilder builder;

    List<BackendDataListModelLinks> pageList = new ArrayList<>();

    String title;

    String desc;

    String image;

    String link;

    public BackendDataListModel() {
    }

    @PostConstruct
    protected void init() throws RepositoryException {

//        for(String x : tags){
//            LOGGER.info(x);
//        }
//        LOGGER.info("path",path);
//        LOGGER.info("tags",tags);
        Map<String, String> predicate = new HashMap<>();
        Session session = resourceResolver.adaptTo(Session.class);
        PageManager pm = resource.getResourceResolver().adaptTo(PageManager.class);

        predicate.put("type", "cq:Page");
        predicate.put("path", path);
        if(presets.equals("OR"))
        {
            predicate.put("group.p.or","true");
        }
        else{
            predicate.put("group.p.and","true");
            }
        int n=1;
        for (String x : tags){
            predicate.put("group."+String.valueOf(n)+"_property", "jcr:content/cq:tags");
            predicate.put("group."+String.valueOf(n)+"_property.value", x);
            n++;
        }

        Query query = builder.createQuery(PredicateGroup.create(predicate), session);

        SearchResult searchResult = query.getResult();

        for (Hit hit : searchResult.getHits()) {
            Resource resource = hit.getResource();
            Page page = pm.getContainingPage(resource);
            ValueMap pageProperties = page.getProperties();
            desc = pageProperties.get("jcr:description", String.class);
            title = pageProperties.get("pageTitle", String.class);
            image = "";
            if ((resource.getChild("jcr:content").getChild("image")) != null) {
                if (resource.getChild("jcr:content").getChild("image").getValueMap().get("fileReference", String.class) != null) {
                    image = resource.getChild("jcr:content").getChild("image").getValueMap().get("fileReference", String.class);
                }

            }

            String link = hit.getPath();
            BackendDataListModelLinks backendDataListModelLinks = new BackendDataListModelLinks();

            backendDataListModelLinks.setTitle(title);
            backendDataListModelLinks.setDesc(desc);
            backendDataListModelLinks.setImage(image);
            backendDataListModelLinks.setLink(link);

            pageList.add(backendDataListModelLinks);
        }
        session.save();
    }

    public List<BackendDataListModelLinks> getPageList() {
        return pageList;
    }

    public String getPath() {
        return path;
    }

    List<String> getTags() {
        return tags;
    }
    public String getPresets() {
        return presets;
    }

}
