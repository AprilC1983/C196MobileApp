package com.example.acmay.c196mobileapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TermDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTerm(TermEntity termEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TermEntity> terms);

    @Delete
    void deleteTerm(TermEntity termEntity);

    @Query("SELECT * FROM terms WHERE termID = :id")
    TermEntity getTermById(int id);

    @Query("SELECT * FROM terms ORDER BY createDate DESC")
    LiveData<List<TermEntity>> getAll();

    @Query("DELETE FROM terms")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM terms")
    int getCount();

    @Query("SELECT COUNT(*) FROM courses WHERE termID = :id")
    int getCourses(int id);
}