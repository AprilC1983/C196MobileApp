package com.example.acmay.c196mobileapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.acmay.c196mobileapp.database.AssessmentEntity;
import com.example.acmay.c196mobileapp.ui.AssessmentAdapter;
import com.example.acmay.c196mobileapp.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AssessmentDisplayActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @OnClick(R.id.edit_fab)
    void fabClickHandler(){
        Intent intent = new Intent(this, AssessmentEditorActivity.class);
        startActivity(intent);
    }

    private List<AssessmentEntity> assessmentsData = new ArrayList<>();
    private AssessmentAdapter mAdapter;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }


    private void initViewModel() {

        final Observer<List<AssessmentEntity>> assessmentsObserver = new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(@Nullable List<AssessmentEntity> assessmentEntities) {
                assessmentsData.clear();
                assessmentsData.addAll(assessmentEntities);

                if(mAdapter == null){
                    mAdapter = new AssessmentAdapter(assessmentsData, AssessmentDisplayActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                } else{
                    mAdapter.notifyDataSetChanged();
                }
            }
        };

        mViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);

        mViewModel.mAssessments.observe(this, assessmentsObserver);
    }


    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(
                mRecyclerView.getContext(), layoutManager.getOrientation());

        mRecyclerView.addItemDecoration(divider);

    }
}