package com.mysite.core.services.impl;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Employee Class Configuration")
public @interface EmployeeConfiguration {
    @AttributeDefinition(
            name = "orgName",
            type = AttributeType.STRING
    )
    String getOrgName();

    @AttributeDefinition(
            name = "orgDesc",
            type = AttributeType.STRING
    )
    String getOrgDesc();

    @AttributeDefinition(
            name = "totalEmp",
            type = AttributeType.INTEGER
    )
    int getTotalEmp();

    @AttributeDefinition(
            name = "orgLocation",
            type = AttributeType.STRING
    )
    String getLocation();

    @AttributeDefinition(
            name = "services",
            type = AttributeType.STRING,
            cardinality = Integer.MAX_VALUE
    )
    String[] getServices();
}
