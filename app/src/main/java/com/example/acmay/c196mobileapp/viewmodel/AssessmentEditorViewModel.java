package com.example.acmay.c196mobileapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.acmay.c196mobileapp.database.AppRepository;
import com.example.acmay.c196mobileapp.database.AssessmentEntity;
import com.example.acmay.c196mobileapp.database.TermEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AssessmentEditorViewModel extends AndroidViewModel {

    public MutableLiveData<AssessmentEntity> mLiveAssessment =
            new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public AssessmentEditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(final int assessmentId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                AssessmentEntity assessment = mRepository.getAssessmentById(assessmentId);
                mLiveAssessment.postValue(assessment);
            }
        });
    }

    public void saveAssessment(String assessmentText) {
        AssessmentEntity assessment = mLiveAssessment.getValue();

        if(assessment == null){
            if(TextUtils.isEmpty(assessmentText.trim())){
                return;
            }
            assessment = new AssessmentEntity(new Date(), assessmentText.trim());
        } else{
            assessment.setText(assessmentText.trim());
        }
        mRepository.insertAssessment(assessment);
    }

    public void deleteAssessment() {
        mRepository.deleteAssessment(mLiveAssessment.getValue());
    }

}