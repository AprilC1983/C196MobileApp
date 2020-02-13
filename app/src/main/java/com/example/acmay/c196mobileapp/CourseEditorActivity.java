package com.example.acmay.c196mobileapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.acmay.c196mobileapp.database.CourseEntity;
import com.example.acmay.c196mobileapp.viewmodel.CourseViewModel;

import java.sql.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.acmay.c196mobileapp.utilities.Constants.COURSE_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.EDITING_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.TERM_ID_KEY;

public class CourseEditorActivity extends AppCompatActivity {

    @BindView(R.id.course_text)
    TextView courseTextView;
    @BindView(R.id.course_start_text)
    TextView courseStart;
    @BindView(R.id.course_end_text)
    TextView courseEnd;

    //saves entered course data and continues to the assessment editor screen
    @OnClick(R.id.course_continue_btn)
    void continueClickHandler(){
        saveAndReturn();
        Intent intent = new Intent(this, MentorEditorActivity.class);
        intent.putExtra("courseKey", courseID);
        startActivity(intent);
    }

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
    private int term;

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
                    courseTextView.setText(courseEntity.getTitle());
                    courseID = courseEntity.getCourseID();
                }
            }
        });

        Bundle extras = getIntent().getExtras();

        if(extras == null){
            //term = extras.getInt(TERM_ID_KEY);
            setTitle(R.string.new_course);
            mNewCourse = true;
        } else {
            setTitle(R.string.edit_course);
            int courseId = extras.getInt(COURSE_ID_KEY);
            mViewModel.loadData(courseId);
            //term = extras.getInt(TERM_ID_KEY);
            term = extras.getInt(TERM_ID_KEY);
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
        String start = courseStart.getText().toString();
        String end = courseEnd.getText().toString();

        mViewModel.saveCourse(term, title, start, end);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }


}