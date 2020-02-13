package com.example.acmay.c196mobileapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "courses"/*, foreignKeys = @ForeignKey(entity = TermEntity.class,
        parentColumns = "termID", childColumns = "termID", onDelete = ForeignKey.CASCADE)*/)
public class CourseEntity {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private int termID;
    private Date createDate;
    private String startDate;
    private String endDate;
    private String title;
    private String status;

    public CourseEntity(int termID, Date createDate, String startDate, String endDate, String title, String status) {
        this.termID = termID;
        this.createDate = createDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.status = status;
    }

    @Ignore
    public CourseEntity(int termID, Date createDate, String title, String startDate, String endDate) {
        this.termID = termID;
        this.createDate = createDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
    }

    @Ignore
    public CourseEntity() {
    }

    @Ignore
    public CourseEntity(int id, Date date, String title) {
        this.termID = id;
        this.createDate = date;
        this.title = title;
    }

    @Ignore
    public CourseEntity(Date date, String title) {
        this.createDate = date;
        this.title = title;
    }

    public int getId() {
        return courseID;
    }

    public void setId(int id) {
        this.courseID = id;
    }

    public Date getDate() {
        return createDate;
    }

    public void setDate(Date date) {
        this.createDate = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    @Override
    public String toString() {
        return "CourseEntity{" +
                "id=" + courseID +
                ", createDate=" + createDate +
                ", title='" + title + '\'' +
                '}';
    }
}
