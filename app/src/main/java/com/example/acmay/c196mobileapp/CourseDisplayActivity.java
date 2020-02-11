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
import android.util.Log;

import com.example.acmay.c196mobileapp.database.CourseEntity;
import com.example.acmay.c196mobileapp.ui.CoursesAdapter;
import com.example.acmay.c196mobileapp.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseDisplayActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static final String TAG = "Course Display";

    @OnClick(R.id.edit_fab)
    void fabClickHandler(){
        Intent intent = new Intent(this, CourseEditorActivity.class);
        startActivity(intent);
        Log.i(TAG, "fabClickHandler: create course");
    }




    private List<CourseEntity> coursesData = new ArrayList<>();
    //private List<CourseEntity> coursesData = new ArrayList<>();
    //private List<AssessmentEntity> assessmentsData = new ArrayList<>();
    private CoursesAdapter mAdapter;
    //private CoursesAdapter cAdapter;
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

        final Observer<List<CourseEntity>> coursesObserver = new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(@Nullable List<CourseEntity> courseEntities) {
                coursesData.clear();
                coursesData.addAll(courseEntities);

                if(mAdapter == null){
                    mAdapter = new CoursesAdapter(coursesData, CourseDisplayActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                } else{
                    mAdapter.notifyDataSetChanged();
                }
            }
        };

        mViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);

        mViewModel.mCourses.observe(this, coursesObserver);
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