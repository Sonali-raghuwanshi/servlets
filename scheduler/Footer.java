package com.mysite.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Required;

import javax.inject.Inject;


    @Model(adaptables = Resource.class)
    public class Footer{

        @Inject
        @Optional
        String text;

        @Inject
        @Default(values = "FOOTER")
        String color;




        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }


        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }


    }


