package com.example.acmay.c196mobileapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.acmay.c196mobileapp.database.AppDatabase;
import com.example.acmay.c196mobileapp.database.AppRepository;
import com.example.acmay.c196mobileapp.database.CourseDao;
import com.example.acmay.c196mobileapp.database.CourseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static android.arch.persistence.room.RoomMasterTable.TABLE_NAME;

public class CourseViewModel extends AndroidViewModel {

    public MutableLiveData<CourseEntity> mLiveCourse =
            new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public CourseViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(final int courseId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                CourseEntity course = mRepository.getCourseById(courseId);
                mLiveCourse.postValue(course);
            }
        });
    }

    public void saveCourse(int termID, String startDate, String endDate, String courseText, String status) {
        CourseEntity course = mLiveCourse.getValue();


        if(course == null){
            if(TextUtils.isEmpty(courseText.trim())){
                return;
            }
            course = new CourseEntity(termID, new Date(), startDate.trim(), endDate.trim(), courseText.trim(), status.trim());
        } else{
            course.setTitle(courseText.trim());
        }
        mRepository.insertCourse(course);
    }

    public void deleteCourse() {
        mRepository.deleteCourse(mLiveCourse.getValue());
    }
}