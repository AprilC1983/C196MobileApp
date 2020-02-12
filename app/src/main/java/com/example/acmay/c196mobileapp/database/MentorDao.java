package com.example.acmay.c196mobileapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MentorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMentor(MentorEntity mentorEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MentorEntity> mentors);

    @Delete
    void deleteMentor(MentorEntity mentorEntity);

    @Query("SELECT * FROM mentors WHERE mentorID = :id")
    MentorEntity getMentorById(int id);

    @Query("SELECT * FROM mentors ORDER BY createDate DESC")
    LiveData<List<MentorEntity>> getAll();

    @Query("DELETE FROM mentors")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM mentors")
    int getCount();
}