package com.example.acmay.c196mobileapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.acmay.c196mobileapp.database.AppDatabase;
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
    private TermDao mDao;

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context,
                AppDatabase.class).build();
        mDao = mDb.termDao();
        Log.i(TAG, "createDb");
    }

    @After
    public void closeDb(){
        mDb.close();
        Log.i(TAG, "closeDb");
    }

    @Test
    public void createAndRetrieveTerms(){
        mDao.insertAll(SampleData.getData());
        int count = mDao.getCount();
        Log.i(TAG, "createAndRetrieveNotes: count=" + count);
        assertEquals(SampleData.getData().size(), count);
    }

    @Test
    public void compareStrings(){
        mDao.insertAll(SampleData.getData());
        TermEntity original = SampleData.getData().get(0);
        TermEntity fromDb = mDao.getTermById(1);
        assertEquals(original.getText(), fromDb.getText());
        assertEquals(1, fromDb.getId());
    }
}
