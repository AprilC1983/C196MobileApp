package com.example.acmay.c196mobileapp;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.acmay.c196mobileapp.viewmodel.AssessmentViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.acmay.c196mobileapp.utilities.Constants.ASS_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.EDITING_KEY;

public class AssessmentEditorActivity extends AppCompatActivity {


    @BindView(R.id.assessment_text)
    TextView assessmentTextView;
    @BindView(R.id.objective_rb)
    RadioButton objective;
    @BindView(R.id.performance_rb)
    RadioButton performance;
    @BindView(R.id.assessment_due_date)
    TextView dueText;

    public AssessmentEditorActivity() throws ParseException {
    }

    //Saves assessment data and returns to the main screen
    @OnClick(R.id.assessment_save)
    void continueClickHandler(){
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
        saveAndReturn();
    }

    //exits assessment screen without saving assessment data
    @OnClick(R.id.assessment_cancel)
    void cancelClickHandler(){
        finish();
    }


    private AssessmentViewModel mViewModel;
    private boolean mNewNote, mEditing;
    private int course;

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

        /*
        mViewModel.mLiveAssessment.observe(this, new Observer<TermEntity>() {
            @Override
            public void onChanged(@Nullable TermEntity termEntity) {
                if(termEntity != null && !mEditing) {
                    mTextView.setName(termEntity.getName());
                }
            }
        });

         */

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            setTitle(R.string.new_assessment);
            mNewNote = true;
        } else {
            setTitle(R.string.edit_assessment);
            int assessmentId = extras.getInt(ASS_ID_KEY);
            mViewModel.loadData(assessmentId);
            course = extras.getInt("courseKey");
            //dueDate = new SimpleDateFormat("MM/dd/yyyy").parse(dueText.toString());
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

        if(objective.isChecked()){
            type = "Objective";
        }else if(performance.isChecked()){
            type = "Performance";
        }

        mViewModel.saveAssessment(course, dueDate, title, type);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }


}