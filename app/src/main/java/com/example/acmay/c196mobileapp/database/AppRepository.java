package com.example.acmay.c196mobileapp.database;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.acmay.c196mobileapp.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static AppRepository ourInstance;

    public LiveData<List<TermEntity>> mTerms;
    public LiveData<List<CourseEntity>> mCourses;
    private AppDatabase mDb;
    private Executor executer = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {

        mDb = AppDatabase.getInstance(context);
        mTerms = getAllTerms();
        mCourses = getAllCourses();
    }

    private LiveData<List<CourseEntity>> getAllCourses() {
        return mDb.courseDao().getAll();
    }

    public void addSampleData() {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                mDb.termDao().insertAll(SampleData.getData());
            }
        });
    }

    private LiveData<List<TermEntity>> getAllTerms(){
        return mDb.termDao().getAll();
    }

    public void deleteAllTerms() {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                mDb.termDao().deleteAll();
            }
        });
    }

    public TermEntity getTermById(int termId) {
        return mDb.termDao().getTermById(termId);
    }

    public void insertTerm(final TermEntity term) {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                mDb.termDao().insertTerm(term);
            }
        });
    }

    public void deleteTerm(final TermEntity term) {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                mDb.termDao().deleteTerm(term);
            }
        });
    }

    public CourseEntity getCourseById(int courseId) {
        return mDb.courseDao().getCourseById(courseId);
    }

    public void insertCourse(CourseEntity course) {
    }

    public void deleteCourse(CourseEntity value) {
    }
}