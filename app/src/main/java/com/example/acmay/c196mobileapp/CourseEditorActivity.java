package com.example.acmay.c196mobileapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.acmay.c196mobileapp.database.TermEntity;
import com.example.acmay.c196mobileapp.viewmodel.CourseEditorViewModel;
import com.example.acmay.c196mobileapp.viewmodel.TermEditorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.acmay.c196mobileapp.utilities.Constants.COURSE_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.EDITING_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.TERM_ID_KEY;

public class CourseEditorActivity extends AppCompatActivity {


    @BindView(R.id.course_text)
    TextView courseTextView;

    /*
    @BindView(R.id.course_start_text)
    TextView courseStsrtText;
    @BindView(R.id.course_end_text)
    TextView courseEndText;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.in_progress_rb)
    RadioButton inProgressBtn;
    @BindView(R.id.completed_rb)
    RadioButton completedRadioBtn;
    @BindView(R.id.dropped_rb)
    RadioButton droppedRadioBtn;
    @BindView(R.id.plan_to_take_rb)
    RadioButton planToTakeRadioBtn;
    @BindView(R.id.cancel_btn)
    Button cancelBtn;
    @BindView(R.id.save_btn)
    Button saveBtn;
    @BindView(R.id.save_and_continue_btn)
    Button saveAndContinueBtn;



     */




    private CourseEditorViewModel mViewModel;
    private boolean mNewNote, mEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        if(savedInstanceState != null){
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }

        initViewModel();
    }


    private void initViewModel(){
        mViewModel = ViewModelProviders.of(this)
                .get(CourseEditorViewModel.class);

        //NEED TO SET UP COURSEENTITY
/*
        mViewModel.mLiveTerm.observe(this, new Observer<TermEntity>() {
            @Override
            public void onChanged(@Nullable TermEntity termEntity) {
                if(termEntity != null && !mEditing) {
                    courseTextView.setText(termEntity.getText());
                }
            }
        });


 */
        Bundle extras = getIntent().getExtras();
        if(extras == null){
            setTitle(R.string.new_course);
            mNewNote = true;
        } else {
            setTitle(R.string.edit_course);
            int termId = extras.getInt(COURSE_ID_KEY);
            mViewModel.loadData(termId);
        }
    }

/*
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
            mViewModel.deleteTerm();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


 */
    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        mViewModel.saveTerm(courseTextView.getText().toString());
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }


}