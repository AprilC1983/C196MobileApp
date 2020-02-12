package com.example.acmay.c196mobileapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.acmay.c196mobileapp.database.AppDatabase;
import com.example.acmay.c196mobileapp.database.CourseDao;
import com.example.acmay.c196mobileapp.database.CourseEntity;
import com.example.acmay.c196mobileapp.database.NoteDao;
import com.example.acmay.c196mobileapp.database.NoteEntity;
import com.example.acmay.c196mobileapp.database.TermDao;
import com.example.acmay.c196mobileapp.database.TermEntity;
import com.example.acmay.c196mobileapp.utilities.SampleData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    public static final String TAG = "Junit";
    private AppDatabase mDb;
    private TermDao tDao;
    private CourseDao cDao;
    private NoteDao nDao;

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context,
                AppDatabase.class).build();
        tDao = mDb.termDao();
        cDao = mDb.courseDao();
        nDao = mDb.noteDao();
        Log.i(TAG, "createDb");
    }

    @After
    public void closeDb(){
        mDb.close();
        Log.i(TAG, "closeDb");
    }

    //Test terms
    //Test to verify creation and retrieval of terms
    @Test
    public void createAndRetrieveTerms(){
        tDao.insertAll(SampleData.getTermsData());
        int termCount = tDao.getCount();

        Log.i(TAG, "createAndRetrieveNotes: count=" + termCount);
        assertEquals(SampleData.getTermsData().size(), termCount);
    }

    //Test to compare term strings
    @Test
    public void compareTermStrings(){
        tDao.insertAll(SampleData.getTermsData());
        TermEntity original = SampleData.getTermsData().get(0);
        TermEntity fromDb = tDao.getTermById(1);
        assertEquals(original.getTitle(), fromDb.getTitle());
        assertEquals(1, fromDb.getId());
    }

    //Test Courses
    //Test to verify creation and retrieval of courses
    @Test
    public void createAndRetrieveCourses(){
        cDao.insertAll(SampleData.getCoursesData());
        int courseCount = cDao.getCount();

        Log.i(TAG, "createAndRetrieveNotes: count=" + courseCount);
        assertEquals(SampleData.getCoursesData().size(), courseCount);
    }

    //Test to compare course strings
    @Test
    public void compareCourseStrings(){
        cDao.insertAll(SampleData.getCoursesData());
        CourseEntity original = SampleData.getCoursesData().get(0);
        CourseEntity fromDb = cDao.getCourseById(1);
        assertEquals(original.getTitle(), fromDb.getTitle());
        assertEquals(1, fromDb.getId());
    }

    //Test Notes
    //Test to verify creation and retrieval of notes
    @Test
    public void createAndRetrieveNotes(){
        nDao.insertAll(SampleData.getNotesData());
        int noteCount = nDao.getCount();

        Log.i(TAG, "createAndRetrieveNotes: count=" + noteCount);
        assertEquals(SampleData.getNotesData().size(), noteCount);
    }

    //Test to compare note strings
    @Test
    public void compareNoteStrings(){
        nDao.insertAll(SampleData.getNotesData());
        NoteEntity original = SampleData.getNotesData().get(0);
        NoteEntity fromDb = nDao.getNoteById(1);
        assertEquals(original.getText(), fromDb.getText());
        assertEquals(1, fromDb.getId());
    }


}