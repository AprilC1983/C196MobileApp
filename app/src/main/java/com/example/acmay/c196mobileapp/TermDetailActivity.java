package com.example.acmay.c196mobileapp;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.acmay.c196mobileapp.database.TermEntity;
import com.example.acmay.c196mobileapp.viewmodel.TermDetailViewModel;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//import static com.example.acmay.c196mobileapp.utilities.Constants.TERM_DETAIL_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.CHANNEL_ID;
import static com.example.acmay.c196mobileapp.utilities.Constants.EDITING_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.TERM_ID_KEY;

public class TermDetailActivity extends AppCompatActivity {


    @BindView(R.id.term_title_text)
    TextView termDetailTextView;
    @BindView(R.id.term_start_text)
    TextView termStart;
    @BindView(R.id.term_end_text)
    TextView termEnd;

    //exits term detail screen
    @OnClick(R.id.term_detail_exit)
    void cancelClickHandler() {
        finish();
    }

    @OnClick(R.id.delete_term_btn)
    void delete() {
        mViewModel.deleteTerm(TermDetailActivity.this);

    }

    private TermDetailViewModel mViewModel;
    private boolean mNewTermDetail, mEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }

        initViewModel();
    }


    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(TermDetailViewModel.class);


        mViewModel.mLiveTerm.observe(this, new Observer<TermEntity>() {
            @Override
            public void onChanged(@Nullable TermEntity termEntity) {
                if (termEntity != null && !mEditing) {
                    termDetailTextView.setText("Term: " + termEntity.getTitle());
                    termStart.setText("Start Date: " + termEntity.getStartDate());
                    termEnd.setText("End Date: " + termEntity.getEndDate());

                    Date start = termEntity.getStartDate();

                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle(R.string.selected_term);
            mNewTermDetail = true;
        } else {
            setTitle(R.string.selected_term);
            int termDetailId = extras.getInt(TERM_ID_KEY);
            mViewModel.loadData(termDetailId);

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