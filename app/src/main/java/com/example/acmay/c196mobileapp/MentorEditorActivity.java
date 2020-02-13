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

import com.example.acmay.c196mobileapp.database.MentorEntity;
import com.example.acmay.c196mobileapp.viewmodel.MentorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.acmay.c196mobileapp.utilities.Constants.COURSE_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.EDITING_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.MENTOR_ID_KEY;

public class MentorEditorActivity extends AppCompatActivity {


    @BindView(R.id.mentor_text)
    TextView mTextView;
    @BindView(R.id.phone_text)
    TextView phoneText;
    @BindView(R.id.email_text)
    TextView emailText;

    /*
    //Saves the Mentor information and continues to the new course screen
    @OnClick(R.id.mentor_continue_btn)
    void continueClickHandler(){
        saveAndReturn();
        Intent intent = new Intent(this, AssessmentEditorActivity.class);
        intent.putExtra(COURSE_ID_KEY, courseId);
        Log.i("editorkeys", "continueClickHandler: from mentor editor is " + courseId);
        startActivity(intent);

    }

     */

    //Exits the create Mentor screen without saving
    @OnClick(R.id.mentor_cancel_btn)
    void cancelClickHandler(){
        finish();
    }

    //Saves Mentor data without continuing to the course creation screen
    @OnClick(R.id.mentor_save_btn)
    void saveClickHandler(){
        saveAndReturn();
    }

    private MentorViewModel mViewModel;
    private boolean mNewMentor, mEditing;
    private int courseId;
    private int mentorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mentor_editor);
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
                .get(MentorViewModel.class);

        mViewModel.mLiveMentor.observe(this, new Observer<MentorEntity>() {
            @Override
            public void onChanged(@Nullable MentorEntity mentorEntity) {
                if(mentorEntity != null && !mEditing) {
                    mTextView.setText(mentorEntity.getName());
                    mentorId = mentorEntity.getMentorID();
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            setTitle(R.string.new_mentor);
            mNewMentor = true;
        } else {
            setTitle(R.string.edit_mentor);
            mentorId = extras.getInt(MENTOR_ID_KEY);
            mViewModel.loadData(mentorId);
            courseId = extras.getInt(COURSE_ID_KEY);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!mNewMentor){
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
            mViewModel.deleteMentor();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        String name = mTextView.getText().toString();
        String phone = phoneText.getText().toString();
        String email = emailText.getText().toString();

        mViewModel.saveMentor(courseId, name, phone, email);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }


}