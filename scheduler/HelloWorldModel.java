/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.mysite.core.models;

import com.mysite.core.services.Employee;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;

import java.util.Arrays;
import java.util.List;

@Model(adaptables = Resource.class)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class HelloWorldModel{
	String orgName;
	String orgDesc;
	int totalEmployee;
	String location;
	String[] services;

	@OSGiService
	Employee employee;

	public String getOrgName() {
		return employee.getOrgName();
	}

	public String getOrgDesc() {
		return employee.getOrgDesc();
	}

	public int getTotalEmployee() {
		return employee.getTotalEmp();
	}

	public String getLocation() {
		return employee.getLocation();
	}

	public String[] getService() {
		return employee.getServices();
	}

	//    @PostConstruct
//    protected void init() {
//        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
//        String currentPagePath = Optional.ofNullable(pageManager)
//                .map(pm -> pm.getContainingPage(currentResource))
//                .map(Page::getPath).orElse("");
//
//        message = "Hello World!\n"
//            + "Resource type is: " + resourceType + "\n"
//            + "Current page is:  " + currentPagePath + "\n"
//            + "This is instance: " + settings.getSlingId() + "\n";
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
}
