package com.example.acmay.c196mobileapp.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import static android.arch.persistence.room.Room.databaseBuilder;

@Database(entities = {TermEntity.class, CourseEntity.class}, version = 2)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    static final Migration MIGRATION_1_2 = new Migration(1, 2){
        @Override
        public void migrate(SupportSQLiteDatabase database){
            database.execSQL("CREATE TABLE courses(id INTEGER NOT NULL, text TEXT, date INTEGER, PRIMARY KEY (id))");
        }
    };

    public static final String DATABASE_NAME = "AppDatabase.db";
    private static volatile AppDatabase instance;
    private static final Object LOCK = new Object();

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();

    public static AppDatabase getInstance(Context context) {

        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME).addMigrations(MIGRATION_1_2).build();
                }
            }
        }
        return instance;
    }
}