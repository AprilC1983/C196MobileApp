package com.example.acmay.c196mobileapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.acmay.c196mobileapp.database.NoteEntity;
import com.example.acmay.c196mobileapp.viewmodel.NoteViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.acmay.c196mobileapp.utilities.Constants.COURSE_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.EDITING_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.NOTE_ID_KEY;

public class NoteEditorActivity extends AppCompatActivity {


    @BindView(R.id.note_text)
    TextView noteTextView;

    //exits Note screen without saving data
    @OnClick(R.id.note_cancel)
    void cancelClickHandler(){
        Intent intent = new Intent(this, CourseDetailActivity.class);
        intent.putExtra(COURSE_ID_KEY, courseId);
        startActivity(intent);
        finish();
    }

    //Saves Note data without continuing to the assessment editor
    @OnClick(R.id.note_save)
    void saveClickHandler(){
        Intent intent = new Intent(this, NoteDisplayActivity.class);
        intent.putExtra(COURSE_ID_KEY, courseId);
        startActivity(intent);
        saveAndReturn();
    }


    private NoteViewModel mViewModel;
    private boolean mNewNote, mEditing;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_editor);
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
                .get(NoteViewModel.class);


        mViewModel.mLiveNote.observe(this, new Observer<NoteEntity>() {
            @Override
            public void onChanged(@Nullable NoteEntity noteEntity) {
                if(noteEntity != null && !mEditing) {
                    noteTextView.setText(noteEntity.getText());
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            setTitle(R.string.edit_note);
            mNewNote = true;
        } else {
            setTitle(R.string.edit_note);
            int noteId = extras.getInt(NOTE_ID_KEY);
            mViewModel.loadData(noteId);
            courseId = extras.getInt(COURSE_ID_KEY);
            Log.i("nid", "initViewModel: cid is " + courseId);
        }
    }


    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        mViewModel.saveNote(courseId, noteTextView.getText().toString());
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }


}