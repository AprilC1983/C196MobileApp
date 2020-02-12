package com.example.acmay.c196mobileapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "mentors")
public class MentorEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private String name;

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

    private String phone;
    private String email;

    @Ignore
    public MentorEntity(int id, Date date, String name, String phone, String email) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Ignore
    public MentorEntity() {
    }

    public MentorEntity(int id, Date date, String name) {
        this.id = id;
        this.date = date;
        this.name = name;
    }

    @Ignore
    public MentorEntity(Date date, String name) {
        this.date = date;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MentorEntity{" +
                "id=" + id +
                ", date=" + date +
                ", name='" + name + '\'' +
                '}';
    }
}