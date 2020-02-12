package com.example.acmay.c196mobileapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "terms")
public class TermEntity {
    @PrimaryKey(autoGenerate = true)
    private int termID;

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    private Date createDate;
    private Date startDate;
    private Date endDate;
    private String title;

    @Ignore
    public TermEntity() {
    }

    public TermEntity(int id, Date createDate, String title) {
        this.termID = id;
        this.createDate = createDate;
        this.title = title;
    }

    @Ignore
    public TermEntity(Date createDate, String title) {
        this.createDate = createDate;
        this.title = title;
    }

    public int getId() {
        return termID;
    }

    public void setId(int id) {
        this.termID = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "TermEntity{" +
                "id=" + termID +
                ", createDate=" + createDate +
                ", title='" + title + '\'' +
                '}';
    }
}