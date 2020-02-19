package com.example.acmay.c196mobileapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.acmay.c196mobileapp.database.CourseEntity;
import com.example.acmay.c196mobileapp.viewmodel.CourseDetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.acmay.c196mobileapp.utilities.Constants.ASS_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.COURSE_ALERT;
import static com.example.acmay.c196mobileapp.utilities.Constants.COURSE_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.EDITING_KEY;

public class AlertActivity extends AppCompatActivity {


    @BindView(R.id.alert_msg)
    TextView alertTxt;
    @BindView(R.id.ok_btn)
    Button okBtn;
    @BindView(R.id.ignore_btn)
    Button ignoreBtn;


    //exits Course detail screen
    @OnClick(R.id.ignore_btn)
    void ignoreClickHandler(){
        finish();
    }

    //open the note editor activity to add a note
    @OnClick(R.id.ok_btn)
    void addClickHandler(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean mEditing;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        if(savedInstanceState != null){
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            setTitle("Error");
            alertTxt.setText("Sorry, message unavailable");
        } else {
            setTitle("Alert");
            String message = (String) extras.get(COURSE_ALERT);
            alertTxt.setText(message);
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


}