package com.example.acmay.c196mobileapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.acmay.c196mobileapp.database.AssessmentEntity;
import com.example.acmay.c196mobileapp.viewmodel.AssessmentViewModel;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.acmay.c196mobileapp.utilities.Constants.ASS_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.COURSE_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.EDITING_KEY;

public class AssessmentEditorActivity extends AppCompatActivity {


    @BindView(R.id.assessment_text)
    TextView assessmentTextView;
    @BindView(R.id.objective_rb)
    RadioButton objectiveRb;
    @BindView(R.id.performance_rb)
    RadioButton performanceRb;
    @BindView(R.id.assessment_due_date)
    TextView dueText;

    public AssessmentEditorActivity() throws ParseException {
    }

    //Saves assessment data and returns to the main screen
    @OnClick(R.id.assessment_save)
    void continueClickHandler(){
        saveAndReturn();
    }

    //exits assessment screen without saving assessment data
    @OnClick(R.id.assessment_cancel)
    void cancelClickHandler(){
        finish();
    }


    private AssessmentViewModel mViewModel;
    private boolean mNewNote, mEditing;
    private int courseId;
    private int assId;
    String perf = "Performance";
    String obj = "Objective";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_editor);
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
                .get(AssessmentViewModel.class);


        mViewModel.mLiveAssessment.observe(this, new Observer<AssessmentEntity>() {
            @Override
            public void onChanged(@Nullable AssessmentEntity assessmentEntity) {
                if(assessmentEntity != null && !mEditing) {
                    String assessmentType = assessmentEntity.getType();

                    assessmentTextView.setText(assessmentEntity.getTitle());
                    dueText.setText(assessmentEntity.getDueDate());

                    if(assessmentType == perf){
                        performanceRb.setChecked(true);
                    }else if(assessmentType == obj){
                        objectiveRb.setChecked(true);
                    }
                    //assId = assessmentEntity.getId();
                    //courseId = assessmentEntity.getCourseID();
                }
            }
        });



        Bundle extras = getIntent().getExtras();
        if(extras == null){
            setTitle(R.string.new_assessment);
            mNewNote = true;
        } else {
            setTitle(R.string.edit_assessment);
            assId = extras.getInt(ASS_ID_KEY);
            mViewModel.loadData(assId);
            courseId = extras.getInt(COURSE_ID_KEY);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!mNewNote){
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
            mViewModel.deleteAssessment();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        String title = assessmentTextView.getText().toString();
        String dueDate = dueText.getText().toString();
        String type = "";

        if(objectiveRb.isChecked()){
            type = obj;
        }else if(performanceRb.isChecked()){
            type = perf;
        }

        mViewModel.saveAssessment(courseId, dueDate, title, type);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }


}