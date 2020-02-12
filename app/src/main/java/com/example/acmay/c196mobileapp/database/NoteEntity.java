package com.example.acmay.c196mobileapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "notes", foreignKeys = @ForeignKey(entity = CourseEntity.class,
        parentColumns = "courseID", childColumns = "courseID", onDelete = ForeignKey.CASCADE))
public class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    private int noteID;
    private int courseID;
    private Date date;
    private String text;

    public NoteEntity(int noteID, int courseID, Date date, String text) {
        this.noteID = noteID;
        this.courseID = courseID;
        this.date = date;
        this.text = text;
    }

    @Ignore
    public NoteEntity() {
    }

    @Ignore
    public NoteEntity(int id, Date date, String text) {
        this.noteID = id;
        this.date = date;
        this.text = text;
    }

    @Ignore
    public NoteEntity(Date date, String text) {
        this.date = date;
        this.text = text;
    }

    public int getId() {
        return noteID;
    }

    public void setId(int id) {
        this.noteID = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + noteID +
                ", date=" + date +
                ", text='" + text + '\'' +
                '}';
    }
}
