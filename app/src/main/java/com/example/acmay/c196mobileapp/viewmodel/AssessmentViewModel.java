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

public class AssessmentViewModel extends AndroidViewModel {

    public MutableLiveData<AssessmentEntity> mLiveAssessment =
            new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public AssessmentViewModel(@NonNull Application application) {
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

    public void saveAssessment(int course, String dueDate, String title, String type) {
        AssessmentEntity assessment = mLiveAssessment.getValue();

        if(assessment == null){
            if(TextUtils.isEmpty(title.trim())){
                return;
            }
            assessment = new AssessmentEntity(course, new Date(), dueDate, title.trim(), type.trim());
        } else{
            assessment.setText(title.trim());
            assessment.setDueDate(dueDate.trim());
            assessment.setType(type.trim());
        }
        mRepository.insertAssessment(assessment);
    }

    public void deleteAssessment() {
        mRepository.deleteAssessment(mLiveAssessment.getValue());
    }

}