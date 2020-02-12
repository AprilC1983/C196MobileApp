package com.example.acmay.c196mobileapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.acmay.c196mobileapp.database.CourseEntity;
import com.example.acmay.c196mobileapp.viewmodel.CourseDetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.acmay.c196mobileapp.utilities.Constants.COURSE_DETAIL_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.EDITING_KEY;

public class CourseDetailActivity extends AppCompatActivity {


    @BindView(R.id.course_title_text)
    TextView courseDetailTextView;
    @BindView(R.id.course_start_text)
    TextView courseStart;
    @BindView(R.id.course_end_text)
    TextView courseEnd;

    //Exits Course detail screen and returns user to the list of courses
    @OnClick(R.id.course_detail_exit)
    void continueClickHandler(){
        Intent intent = new Intent(this, CourseDisplayActivity.class);
        startActivity(intent);
        finish();
    }

    //exits Course detail screen
    @OnClick(R.id.course_detail_exit)
    void cancelClickHandler(){
        finish();
    }

    //open the note editor activity to add a note
    @OnClick(R.id.add_note_btn)
    void addClickHandler(){
        Intent intent = new Intent(this, NoteEditorActivity.class);
        startActivity(intent);
        finish();
    }

    //view existing notes
    @OnClick(R.id.view_notes_btn)
    void viewClickHandler(){
        Intent intent = new Intent(this, NoteDisplayActivity.class);
        startActivity(intent);
        finish();
    }

    private CourseDetailViewModel mViewModel;
    private boolean mNewCourseDetail, mEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        if(savedInstanceState != null){
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }

        initViewModel();
    }


    private void initViewModel(){
        mViewModel = ViewModelProviders.of(this)
                .get(CourseDetailViewModel.class);


        mViewModel.mLiveCourse.observe(this, new Observer<CourseEntity>() {
            @Override
            public void onChanged(@Nullable CourseEntity courseEntity) {
                if(courseEntity != null && !mEditing) {
                    courseDetailTextView.setText(courseEntity.getTitle());
                    courseStart.setText("Start Date: " + courseEntity.getStartDate());
                    courseEnd.setText("End Date: " + courseEntity.getEndDate());
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            setTitle(R.string.selected_course);
            mNewCourseDetail = true;
        } else {
            setTitle(R.string.selected_course);
            int courseDetailId = extras.getInt(COURSE_DETAIL_ID_KEY);
            mViewModel.loadData(courseDetailId);
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


}