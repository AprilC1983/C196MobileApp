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

import com.example.acmay.c196mobileapp.database.TermEntity;
import com.example.acmay.c196mobileapp.viewmodel.TermEditorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.acmay.c196mobileapp.utilities.Constants.EDITING_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.TERM_ID_KEY;

public class TermEditorActivity extends AppCompatActivity {


    @BindView(R.id.term_text)
    TextView mTextView;

    //Saves the term information and continues to the new course screen
    @OnClick(R.id.term_continue_btn)
    void continueClickHandler(){
        saveAndReturn();
        Intent intent = new Intent(this, CourseEditorActivity.class);
        startActivity(intent);

    }

    //Exits the create term screen without saving
    @OnClick(R.id.term_cancel_btn)
    void cancelClickHandler(){
        finish();
    }

    //Saves term data without continuing to the course creation screen
    @OnClick(R.id.term_save_btn)
    void saveClickHandler(){
        saveAndReturn();
    }

    private TermEditorViewModel mViewModel;
    private boolean mNewTerm, mEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
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
                .get(TermEditorViewModel.class);

        mViewModel.mLiveTerm.observe(this, new Observer<TermEntity>() {
            @Override
            public void onChanged(@Nullable TermEntity termEntity) {
                if(termEntity != null && !mEditing) {
                    mTextView.setText(termEntity.getText());
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            setTitle(R.string.new_term);
            mNewTerm = true;
        } else {
            setTitle(R.string.edit_term);
            int termId = extras.getInt(TERM_ID_KEY);
            mViewModel.loadData(termId);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!mNewTerm){
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

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        mViewModel.saveTerm(mTextView.getText().toString());
        finish();
    }

    private void save(){
        mViewModel.saveTerm(mTextView.getText().toString());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }


}