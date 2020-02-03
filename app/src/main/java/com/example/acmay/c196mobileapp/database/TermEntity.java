package com.example.acmay.c196mobileapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "terms")
public class TermEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private String text;

    @Ignore
    public TermEntity() {
    }

    public TermEntity(int id, Date date, String text) {
        this.id = id;
        this.date = date;
        this.text = text;
    }

    @Ignore
    public TermEntity(Date date, String text) {
        this.date = date;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "TermEntity{" +
                "id=" + id +
                ", date=" + date +
                ", text='" + text + '\'' +
                '}';
    }
}
