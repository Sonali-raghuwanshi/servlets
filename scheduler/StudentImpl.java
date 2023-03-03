package com.mysite.core.services.impl;

import com.mysite.core.services.Student;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = Student.class , immediate = true)
@Designate(ocd = classConfigurationService.class)
public class StudentImpl implements Student {
    int numberOfStudent;
    int marksRequired;
    @Activate
    public void activatedMethod(classConfigurationService config){
        numberOfStudent=config.total_student();
        marksRequired= config.get_passing_marks();
    }

    @Override
    public boolean isClassLimitReached(int list) {
        if(list<numberOfStudent){
            return true;
        }
        else
            return false;
    }

    @Override
    public int getPassingMarks() {
        return marksRequired;
    }
}
