package com.example.acmay.c196mobileapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.acmay.c196mobileapp.database.AppRepository;
import com.example.acmay.c196mobileapp.database.TermEntity;

public class EditorViewModel extends AndroidViewModel {

    public MutableLiveData<TermEntity> mLiveTerm =
            new MutableLiveData<>();
    private AppRepository mRepository;
    public EditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }
}
