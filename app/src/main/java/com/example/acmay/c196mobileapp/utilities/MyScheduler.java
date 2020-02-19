package com.example.acmay.c196mobileapp.utilities;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class MyScheduler extends JobService {
    private static final String TAG = "MyScheduler";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "Job started");

        doBackgroundWork(params);

        return true;
    }

    private void doBackgroundWork(final JobParameters params){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(jobCancelled){
                    return;
                }
                dummyMethod();
                Log.i(TAG, "Job finished");
                jobFinished(params, false);
            }
            
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "Job was cancelled");
        jobCancelled = true;
        return false;
    }

    private void dummyMethod(){
        for(int i = 0; i < 10; i++){
            Log.i(TAG, "dummyMethod: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
