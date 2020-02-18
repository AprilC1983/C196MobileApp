package com.example.acmay.c196mobileapp.database;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Button;

import com.example.acmay.c196mobileapp.Exceptions.HasCoursesAssignedException;
import com.example.acmay.c196mobileapp.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class AppRepository {
    private static AppRepository ourInstance;

    public LiveData<List<TermEntity>> mTerms;
    public LiveData<List<CourseEntity>> mCourses;
    public LiveData<List<AssessmentEntity>> mAssessments;
    public LiveData<List<MentorEntity>> mMentors;
    public LiveData<List<NoteEntity>> mNotess;

    private AppDatabase mDb;
    private Executor executer = Executors.newSingleThreadExecutor();
    //private int numTerms;
    //private int numCourses;

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
        mNotess = getAllNotes();
    }

    public void addSampleData() {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                mDb.termDao().insertAll(SampleData.getTermsData());
                mDb.courseDao().insertAll(SampleData.getCoursesData());
                mDb.assessmentDao().insertAll(SampleData.getAssessmentsData());
                mDb.mentorDao().insertAll(SampleData.getMentorsData());
                mDb.noteDao().insertAll(SampleData.getNotesData());
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


    //This term verifies whether a term has courses assigned to it and deletes the term if co courses are assigned
    public void deleteTerm(final TermEntity term, final Context context) {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                //int courses = mDb.termDao().getCourses(term.getId());
                int numCourses = mDb.termDao().getCourses(term.getId());
                try {
                    if (numCourses != 0) {
                        throw new HasCoursesAssignedException("This term has courses assigned to it");
                    }else if(numCourses == 0) {
                        mDb.termDao().deleteTerm(term);
                    }
                } catch(HasCoursesAssignedException ex){
                    Log.i("oberon", "Do not delete term, " + numCourses + " courses assigned to it");
                    //displayAlert(context);
                }

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

    //Note specific methods
    private LiveData<List<NoteEntity>> getAllNotes(){
        return mDb.noteDao().getAll();
    }

    public void deleteAllNotes() {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().deleteAll();
            }
        });
    }

    public NoteEntity getNoteById(int noteId) {
        return mDb.noteDao().getNoteById(noteId);
    }

    public void insertNote(final NoteEntity note) {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().insertNote(note);
            }
        });
    }

    public void deleteNote(final NoteEntity note) {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().deleteNote(note);
            }
        });
    }

    //Creates an alert dialog
    public void displayAlert(Context context){
        //Create an alert popup
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        adb.setMessage("Cannot delete terms with courses assigned")
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = adb.create();
        alert.setTitle("Error");
        alert.show();
    }
}