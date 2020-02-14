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
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.acmay.c196mobileapp.database.CourseEntity;
import com.example.acmay.c196mobileapp.viewmodel.CourseViewModel;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.acmay.c196mobileapp.utilities.Constants.COURSE_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.EDITING_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.TERM_ID_KEY;

public class CourseEditorActivity extends AppCompatActivity {

    @BindView(R.id.course_text)
    TextView courseTextView;

    @BindView(R.id.course_start_picker)
    DatePicker courseStart;
    @BindView(R.id.course_end_picker)
    DatePicker courseEnd;

    @BindView(R.id.plan_to_take_rb)
    RadioButton plannedRb;
    @BindView(R.id.in_progress_rb)
    RadioButton inProgRb;
    @BindView(R.id.completed_rb)
    RadioButton completedRb;
    @BindView(R.id.dropped_rb)
    RadioButton droppedRb;

    /*
    //saves entered course data and continues to the assessment editor screen
    @OnClick(R.id.course_continue_btn)
    void continueClickHandler(){
        saveAndReturn();
        Intent intent = new Intent(this, MentorEditorActivity.class);
        intent.putExtra(COURSE_ID_KEY, courseID);
        Log.i("editorkeys", "continueClickHandler: from course editor cid is" + courseID);
        startActivity(intent);
    }

     */

    //exits course screen without saving data
    @OnClick(R.id.course_cancel_btn)
    void cancelClickHandler(){
        finish();
    }

    //Saves course data without continuing to the assessment editor
    @OnClick(R.id.course_save_btn)
    void saveClickHandler(){
        saveAndReturn();
    }

    private CourseViewModel mViewModel;
    private boolean mNewCourse, mEditing;
    private int courseID;
    private int termID;
    String planned = "Plan to take";
    String taking = "In Progress";
    String completed = "Completed";
    String dropped = "Dropped";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_editor);
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
                .get(CourseViewModel.class);

        mViewModel.mLiveCourse.observe(this, new Observer<CourseEntity>() {
            @Override
            public void onChanged(@Nullable CourseEntity courseEntity) {
                if(courseEntity != null && !mEditing) {
                    String currentStatus = courseEntity.getStatus();

                    if(currentStatus == planned){
                        plannedRb.setChecked(true);
                    }else if(currentStatus == taking){
                        inProgRb.setChecked(true);
                    }else if(currentStatus == completed){
                        completedRb.setChecked(true);
                    }else if(currentStatus == dropped){
                        droppedRb.setChecked(true);
                    }

                    courseTextView.setText(courseEntity.getTitle());
                    //courseStart.setText(courseEntity.getStartDate());
                    //courseEnd.setText(courseEntity.getEndDate());
                }
            }
        });

        Bundle extras = getIntent().getExtras();

        if(extras == null){
            setTitle(R.string.new_course);
            mNewCourse = true;
        } else {
            setTitle(R.string.edit_course);
            courseID = extras.getInt(COURSE_ID_KEY);
            Log.i("cid", "course id = " + courseID);
            mViewModel.loadData(courseID);
            termID = extras.getInt(TERM_ID_KEY);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!mNewCourse){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_editor, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            saveAndReturn();
            return true;
        } else if(item.getItemId() == R.id.action_delete){
            mViewModel.deleteCourse();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        String title = courseTextView.getText().toString();
        String status = "";

        if(plannedRb.isChecked()){
            status = planned;
        }else if(inProgRb.isChecked()){
            status = taking;
        }else if(completedRb.isChecked()){
            status = completed;
        }else if(droppedRb.isChecked()){
            status = dropped;
        }

        int startDay = courseStart.getDayOfMonth();
        int startMonth = courseStart.getMonth();
        int startYear = courseStart.getYear();

        int endDay = courseEnd.getDayOfMonth();
        int endMonth = courseEnd.getMonth();
        int endYear = courseEnd.getYear();

        java.util.Date start = new java.util.Date(startYear, startMonth - 1, startDay);
        java.util.Date end = new Date(endYear, endMonth - 1, endDay);

            mViewModel.saveCourse(termID, start, end, title, status);
            finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }


}