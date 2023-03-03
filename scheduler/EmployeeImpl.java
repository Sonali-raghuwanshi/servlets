package com.mysite.core.services.impl;

import com.mysite.core.services.Employee;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = Employee.class,immediate=true)
@Designate(ocd= EmployeeConfiguration.class)
public class EmployeeImpl implements Employee{

    String orgName;
    String orgDesc;
    int totalEmp;
    String location;
    String[] services = new String[5];

    @Activate
    @Modified
    public void activate(EmployeeConfiguration configuration){
        orgName = configuration.getOrgName();
        orgDesc = configuration.getOrgDesc();
        totalEmp = configuration.getTotalEmp();
        location = configuration.getLocation();
        services = configuration.getServices();
    }

    @Override
    public String getOrgName() {
        return orgName;
    }

    @Override
    public String getOrgDesc() {
        return orgDesc;
    }

    @Override
    public int getTotalEmp() {
        return totalEmp;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public String[] getServices() {
        return services;
    }
}
