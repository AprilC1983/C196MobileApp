package com.example.acmay.c196mobileapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.acmay.c196mobileapp.database.AppRepository;
import com.example.acmay.c196mobileapp.database.TermEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TermDetailViewModel extends AndroidViewModel {

    public MutableLiveData<TermEntity> mLiveTerm =
            new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public TermDetailViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(final int termId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                TermEntity term = mRepository.getTermById(termId);
                mLiveTerm.postValue(term);
            }
        });
    }

    /*
    public void saveTerm(String mentorText) {
        MentorEntity mentor = mLiveMentor.getValue();

        if(mentor == null){
            if(TextUtils.isEmpty(mentorText.trim())){
                return;
            }
            mentor = new MentorEntity(new Date(), mentorText.trim());
        } else{
            mentor.setName(mentorText.trim());
        }
        mRepository.insertMentor(mentor);
    }


    public void deleteMentor() {
        mRepository.deleteMentor(mLiveMentor.getValue());
    }

     */

}