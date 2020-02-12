package com.example.acmay.c196mobileapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.acmay.c196mobileapp.database.AppRepository;
import com.example.acmay.c196mobileapp.database.MentorEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MentorViewModel extends AndroidViewModel {

    public MutableLiveData<MentorEntity> mLiveMentor =
            new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public MentorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(final int mentorId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                MentorEntity mentor = mRepository.getMentorById(mentorId);
                mLiveMentor.postValue(mentor);
            }
        });
    }

    public void saveMentor(int course, String mentorText, String phone, String email) {
        MentorEntity mentor = mLiveMentor.getValue();

        if(mentor == null){
            if(TextUtils.isEmpty(mentorText.trim())){
                return;
            }
            mentor = new MentorEntity(course, new Date(), mentorText.trim(), phone, email);
        } else{
            mentor.setName(mentorText.trim());
        }
        mRepository.insertMentor(mentor);
    }

    public void deleteMentor() {
        mRepository.deleteMentor(mLiveMentor.getValue());
    }

}