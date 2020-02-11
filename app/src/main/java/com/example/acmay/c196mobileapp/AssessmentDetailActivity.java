package com.example.acmay.c196mobileapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.acmay.c196mobileapp.database.AssessmentEntity;
import com.example.acmay.c196mobileapp.viewmodel.AssessmentDetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.acmay.c196mobileapp.utilities.Constants.ASSESSMENT_DETAIL_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.EDITING_KEY;

public class AssessmentDetailActivity extends AppCompatActivity {


    @BindView(R.id.assessment_title_text)
    TextView assessmentDetailTextView;

    //Exits Assessment detail screen and returns user to the list of Assessments
    @OnClick(R.id.assessment_detail_exit)
    void continueClickHandler(){
        Intent intent = new Intent(this, AssessmentDisplayActivity.class);
        startActivity(intent);
        finish();
    }

    //exits Assessment detail screen
    @OnClick(R.id.assessment_detail_exit)
    void cancelClickHandler(){
        finish();
    }

    private AssessmentDetailViewModel mViewModel;
    private boolean mNewAssessmentDetail, mEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_detail);
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
                .get(AssessmentDetailViewModel.class);


        mViewModel.mLiveAssessment.observe(this, new Observer<AssessmentEntity>() {
            @Override
            public void onChanged(@Nullable AssessmentEntity assessmentDetailEntity) {
                if(assessmentDetailEntity != null && !mEditing) {
                    assessmentDetailTextView.setText(assessmentDetailEntity.getText());
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            setTitle(R.string.assessment_name);
            mNewAssessmentDetail = true;
        } else {
            setTitle(R.string.assessment_name);
            int assessmentDetailId = extras.getInt(ASSESSMENT_DETAIL_ID_KEY);
            mViewModel.loadData(assessmentDetailId);
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