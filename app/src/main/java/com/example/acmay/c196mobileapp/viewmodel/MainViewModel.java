package com.example.acmay.c196mobileapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.acmay.c196mobileapp.database.AppRepository;
import com.example.acmay.c196mobileapp.database.TermEntity;
import com.example.acmay.c196mobileapp.utilities.SampleData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public List<TermEntity> mTerms;
    private AppRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mTerms = mRepository.mTerms;
    }

    public void addSampleData() {

        mRepository.addSampleData();
    }
}
