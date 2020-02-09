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

public class AssessmentEditorViewModel extends AndroidViewModel {

    public MutableLiveData<TermEntity> mLiveTerm =
            new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public AssessmentEditorViewModel(@NonNull Application application) {
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

    public void saveTerm(String termText) {
        TermEntity term = mLiveTerm.getValue();

        if(term == null){
            if(TextUtils.isEmpty(termText.trim())){
                return;
            }
            term = new TermEntity(new Date(), termText.trim());
        } else{
            term.setText(termText.trim());
        }
        mRepository.insertTerm(term);
    }

    public void deleteTerm() {
        mRepository.deleteTerm(mLiveTerm.getValue());
    }

}