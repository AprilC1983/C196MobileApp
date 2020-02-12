package com.example.acmay.c196mobileapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "mentors", foreignKeys = @ForeignKey(entity = CourseEntity.class,
        parentColumns = "courseID", childColumns = "courseID", onDelete = ForeignKey.CASCADE))
public class MentorEntity {
    @PrimaryKey(autoGenerate = true)
    private int mentorID;
    private int courseID;
    private Date createDate;
    private String name;
    private String phone;
    private String email;

    public MentorEntity(int mentorID, int courseID, Date createDate, String name, String phone, String email) {
        this.mentorID = mentorID;
        this.courseID = courseID;
        this.createDate = createDate;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Ignore
    public MentorEntity(int id, Date date, String name, String phone, String email) {
        this.mentorID = id;
        this.createDate = date;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Ignore
    public MentorEntity() {
    }

    @Ignore
    public MentorEntity(int id, Date date, String name) {
        this.mentorID = id;
        this.createDate = date;
        this.name = name;
    }

    @Ignore
    public MentorEntity(Date date, String name) {
        this.createDate = date;
        this.name = name;
    }

    public int getId() {
        return mentorID;
    }

    public void setId(int id) {
        this.mentorID = id;
    }

    public Date getDate() {
        return createDate;
    }

    public void setDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getMentorID() {
        return mentorID;
    }

    public void setMentorID(int mentorID) {
        this.mentorID = mentorID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    @Override
    public String toString() {
        return "MentorEntity{" +
                "id=" + mentorID +
                ", date=" + createDate +
                ", name='" + name + '\'' +
                '}';
    }
}