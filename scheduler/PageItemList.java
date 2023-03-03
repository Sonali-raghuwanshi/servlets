package com.pwc.madison.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class PageItemList {


    private String title;
    public String getTitle() {
        return title;
    }

    private String description;
    public String getDescription() {
        return description;
    }

    private String link;
    public String getLink() {
        return link;
    }
    private String thumbnailimage;
    public String getThumbnailimage() {
        return thumbnailimage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setThumbnailimage(String thumbnailimage) {
        this.thumbnailimage = thumbnailimage;
    }
}
