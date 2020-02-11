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
    public LiveData<List<AssessmentEntity>> mAssessments;
    public LiveData<List<MentorEntity>> mMentors;

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
        mAssessments = getAllAssessments();
        mMentors = getAllMentors();
    }

    public void addSampleData() {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                mDb.termDao().insertAll(SampleData.getTermsData());
                mDb.courseDao().insertAll(SampleData.getCoursesData());
                mDb.assessmentDao().insertAll(SampleData.getAssessmentsData());
                mDb.mentorDao().insertAll(SampleData.getMentorsData());
            }
        });
    }


    //Term-specific methods
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

    //Course-specific methods
    private LiveData<List<CourseEntity>> getAllCourses(){
        return mDb.courseDao().getAll();
    }

    public void deleteAllCourses() {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                mDb.courseDao().deleteAll();
            }
        });
    }

    public CourseEntity getCourseById(int courseId) {
        return mDb.courseDao().getCourseById(courseId);
    }

    public void insertCourse(final CourseEntity course) {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                mDb.courseDao().insertCourse(course);
            }
        });
    }

    public void deleteCourse(final CourseEntity course) {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                mDb.courseDao().deleteCourse(course);
            }
        });
    }

    //Assessment-specific methods
    private LiveData<List<AssessmentEntity>> getAllAssessments(){
        return mDb.assessmentDao().getAll();
    }

    public void deleteAllAssessments() {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                mDb.assessmentDao().deleteAll();
            }
        });
    }

    public AssessmentEntity getAssessmentById(int assessmentId) {
        return mDb.assessmentDao().getAssessmentById(assessmentId);
    }

    public void insertAssessment(final AssessmentEntity assessment) {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                mDb.assessmentDao().insertAssessment(assessment);
            }
        });
    }

    public void deleteAssessment(final AssessmentEntity assessment) {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                mDb.assessmentDao().deleteAssessment(assessment);
            }
        });
    }

    //Mentor specific methods
    private LiveData<List<MentorEntity>> getAllMentors(){
        return mDb.mentorDao().getAll();
    }

    public void deleteAllMentors() {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                mDb.mentorDao().deleteAll();
            }
        });
    }

    public MentorEntity getMentorById(int mentorId) {
        return mDb.mentorDao().getMentorById(mentorId);
    }

    public void insertMentor(final MentorEntity mentor) {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                mDb.mentorDao().insertMentor(mentor);
            }
        });
    }

    public void deleteMentor(final MentorEntity mentor) {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                mDb.mentorDao().deleteMentor(mentor);
            }
        });
    }

}