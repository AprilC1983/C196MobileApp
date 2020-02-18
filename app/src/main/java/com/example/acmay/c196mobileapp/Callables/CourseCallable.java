package com.example.acmay.c196mobileapp.Callables;

import com.example.acmay.c196mobileapp.Exceptions.HasCoursesAssignedException;
import com.example.acmay.c196mobileapp.database.AppDatabase;

public class CourseCallable implements CourseInterface<Boolean> {
    boolean coursesFound;

    public CourseCallable(Boolean courses){
        this.coursesFound = courses;
    }
    @Override
    public Boolean call() throws Exception{
        return coursesFound;
    }
}
