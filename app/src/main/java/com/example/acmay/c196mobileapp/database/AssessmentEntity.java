package com.example.acmay.c196mobileapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessments", foreignKeys = @ForeignKey(entity = CourseEntity.class,
        parentColumns = "courseID", childColumns = "courseID", onDelete = ForeignKey.CASCADE))
public class AssessmentEntity {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private int courseID;
    private Date createDate;
    private String title;
    private String type;

    public AssessmentEntity(int assessmentID, int courseID, Date createDate, String title, String type) {
        this.assessmentID = assessmentID;
        this.courseID = courseID;
        this.createDate = createDate;
        this.title = title;
        this.type = type;
    }

    @Ignore
    public AssessmentEntity() {
    }

    @Ignore
    public AssessmentEntity(int id, Date date, String text) {
        this.assessmentID = id;
        this.createDate = date;
        this.title = text;
    }

    @Ignore
    public AssessmentEntity(Date date, String text) {
        this.createDate = date;
        this.title = text;
    }

    public int getId() {
        return assessmentID;
    }

    public void setId(int id) {
        this.assessmentID = id;
    }

    public Date getDate() {
        return createDate;
    }

    public void setDate(Date date) {
        this.createDate = date;
    }

    public String getText() {
        return title;
    }

    public void setText(String text) {
        this.title = text;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    @Override
    public String toString() {
        return "AssessmentEntity{" +
                "id=" + assessmentID +
                ", date=" + createDate +
                ", text='" + title + '\'' +
                '}';
    }
}
