package com.mysite.core.services.impl;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name ="student configuration")
public @interface classConfigurationService {
    @AttributeDefinition(name="No Of Student",description="Enter total number of student",type= AttributeType.INTEGER)

    int total_student();
    @AttributeDefinition(name="Required Marks",description="Enter the required marks",type=AttributeType.INTEGER)
    int get_passing_marks();

}
