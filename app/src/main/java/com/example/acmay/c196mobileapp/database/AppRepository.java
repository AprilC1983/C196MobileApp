package com.example.acmay.c196mobileapp.database;

import android.content.Context;

import com.example.acmay.c196mobileapp.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static AppRepository ourInstance;

    public List<TermEntity> mTerms;
    private AppDatabase mDb;
    private Executor executer = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
        mTerms = SampleData.getData();
        mDb = AppDatabase.getInstance(context);
    }

    public void addSampleData() {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                mDb.termDao().insertAll(SampleData.getData());
            }
        });
    }
}
