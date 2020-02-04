package com.example.acmay.c196mobileapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface StartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStart(StartEntity startEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<StartEntity> start);

    @Delete
    void deleteStart(StartEntity startEntity);

    @Query("SELECT * FROM startdates WHERE id = :id")
    StartEntity getStartById(int id);

    @Query("SELECT * FROM startdates ORDER BY date DESC")
    LiveData<List<StartEntity>> getAll();

    @Query("DELETE FROM startdates")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM startDates")
    int getCount();
}
