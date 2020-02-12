package com.example.acmay.c196mobileapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "terms")
public class TermEntity {
    @PrimaryKey(autoGenerate = true)
    private int termID;
    private Date createDate;
    private String startDate;
    private String endDate;
    private String title;

    public TermEntity(int termID, Date createDate, String startDate, String endDate, String title) {
        this.termID = termID;
        this.createDate = createDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
    }

    @Ignore
    public TermEntity(Date createDate, String startDate, String endDate, String title) {
        this.createDate = createDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
    }

    @Ignore
    public TermEntity() {
    }

    @Ignore
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
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