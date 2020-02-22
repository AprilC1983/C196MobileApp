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
import android.widget.TextView;

import com.example.acmay.c196mobileapp.database.CourseEntity;
import com.example.acmay.c196mobileapp.viewmodel.CourseDetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.acmay.c196mobileapp.utilities.Constants.COURSE_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.EDITING_KEY;

public class CourseDetailActivity extends AppCompatActivity {


    @BindView(R.id.course_title_text)
    TextView courseDetailTextView;
    @BindView(R.id.course_start_text)
    TextView courseStart;
    @BindView(R.id.course_end_text)
    TextView courseEnd;
    @BindView(R.id.status_text)
    TextView courseStatus;

    /*
    //Exits Course detail screen and returns user to the list of courses
    @OnClick(R.id.course_detail_exit)
    void continueClickHandler(){
        Intent intent = new Intent(this, CourseDisplayActivity.class);
        startActivity(intent);
        finish();
    }
*/
    //exits Course detail screen
    @OnClick(R.id.course_detail_exit)
    void cancelClickHandler(){
        finish();
    }

    //open the note editor activity to add a note
    @OnClick(R.id.view_btn)
    void addClickHandler(){
        Intent intent = new Intent(this, NoteDisplayActivity.class);
        intent.putExtra(COURSE_ID_KEY, courseId);
        Log.i("ndis", "addClickHandler: cid is " + courseId);
        startActivity(intent);
        finish();
    }

    //view existing notes
    @OnClick(R.id.add_btn)
    void viewClickHandler(){
        Intent intent = new Intent(this, NoteEditorActivity.class);
        startActivity(intent);
        intent.putExtra(COURSE_ID_KEY, courseId);
        Log.i("ndis", "viewClickHandler: cid is " + courseId);
        finish();
    }

    private CourseDetailViewModel mViewModel;
    private boolean mNewCourseDetail, mEditing;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
/*
        if(savedInstanceState != null){
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }

 */

        initViewModel();
    }


    private void initViewModel(){
        mViewModel = ViewModelProviders.of(this)
                .get(CourseDetailViewModel.class);


        mViewModel.mLiveCourse.observe(this, new Observer<CourseEntity>() {
            @Override
            public void onChanged(@Nullable CourseEntity courseEntity) {
                if(courseEntity != null && !mEditing) {
                    courseDetailTextView.setText("Course Title: " + courseEntity.getTitle());
                    courseStart.setText("Start Date: " + courseEntity.getStartDate());
                    courseEnd.setText("End Date: " + courseEntity.getEndDate());
                    courseStatus.setText("Status: " + courseEntity.getStatus());
                }

            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            setTitle(R.string.selected_course);
            mNewCourseDetail = true;
        } else {
            setTitle(R.string.selected_course);
            courseId = extras.getInt(COURSE_ID_KEY);
            mViewModel.loadData(courseId);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_back){
            Intent intent = new Intent(this, CourseDisplayActivity.class);
            //startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}