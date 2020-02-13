package com.example.acmay.c196mobileapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.acmay.c196mobileapp.database.MentorEntity;
import com.example.acmay.c196mobileapp.viewmodel.MentorDetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//import static com.example.acmay.c196mobileapp.utilities.Constants.MENTOR_DETAIL_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.EDITING_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.MENTOR_ID_KEY;

public class MentorDetailActivity extends AppCompatActivity {


    @BindView(R.id.mentor_title_text)
    TextView mentorDetailTextView;
    @BindView(R.id.mentor_phone)
    TextView phone;
    @BindView(R.id.mentor_email)
    TextView email;

    //Exits Mentor detail screen and returns user to the list of courses
    @OnClick(R.id.mentor_detail_exit)
    void continueClickHandler(){
        finish();
    }

    //exits Mentor detail screen
    @OnClick(R.id.mentor_detail_exit)
    void cancelClickHandler(){
        finish();
    }

    private MentorDetailViewModel mViewModel;
    private boolean mNewMentorDetail, mEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mentor_detail);
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
                .get(MentorDetailViewModel.class);


        mViewModel.mLiveMentor.observe(this, new Observer<MentorEntity>() {
            @Override
            public void onChanged(@Nullable MentorEntity mentorEntity) {
                if(mentorEntity != null && !mEditing) {
                    mentorDetailTextView.setText("Name: " + mentorEntity.getName());
                    phone.setText("Phone: " + mentorEntity.getPhone());
                    email.setText("Email: " + mentorEntity.getEmail());
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            setTitle(R.string.mentor);
            mNewMentorDetail = true;
        } else {
            setTitle(R.string.mentor);
            int mentorDetailId = extras.getInt(MENTOR_ID_KEY);
            mViewModel.loadData(mentorDetailId);
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