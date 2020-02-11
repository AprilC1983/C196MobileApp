package com.example.acmay.c196mobileapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.acmay.c196mobileapp.database.AppRepository;
import com.example.acmay.c196mobileapp.database.CourseEntity;
import com.example.acmay.c196mobileapp.database.TermEntity;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<TermEntity>> mTerms;
    public LiveData<List<CourseEntity>> mCourses;
    private AppRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mTerms = mRepository.mTerms;
        mCourses = mRepository.mCourses;
    }

    public void addSampleData() {

        mRepository.addSampleData();
    }


    public void deleteAllTerms() {
        mRepository.deleteAllTerms();
    }


}