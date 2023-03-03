package com.pwc.madison.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Date;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MostPopularModel1 {

    @Self
    Resource resource;

    private String userName;

    private Date date;

    @PostConstruct
    protected void init(){
        PageManager pm = resource.getResourceResolver().adaptTo(PageManager.class);

        Page containingPage = pm.getContainingPage (resource);

        ValueMap pageProperties = containingPage.getProperties();

        userName=pageProperties.get("cq:lastModifiedBy",String.class);
        date=pageProperties.get("cq:lastModified", Date.class);
    }
    @Inject
    private String RTE;
    public String getRTE() {
        return RTE;
    }

    public String getUserName() {
        return userName;
    }
    public Date getDate() {
        return date;
    }





}
