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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.acmay.c196mobileapp.database.AssessmentEntity;
import com.example.acmay.c196mobileapp.database.CourseEntity;
import com.example.acmay.c196mobileapp.ui.AssessmentAdapter;
import com.example.acmay.c196mobileapp.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.acmay.c196mobileapp.utilities.Constants.ASS_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.COURSE_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.TERM_ID_KEY;

public class AssessmentDisplayActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @OnClick(R.id.add_fab)
    void fabClickHandler(){
        Intent intent = new Intent(this, AssessmentEditorActivity.class);
        intent.putExtra(COURSE_ID_KEY, courseId);
        Log.i("zz", "fabClickHandler assdisplay: cid = " + courseId);
        startActivity(intent);
    }

    private List<AssessmentEntity> allAssessments = new ArrayList<>();
    private List<AssessmentEntity> displayAssessments = new ArrayList<>();
    private AssessmentAdapter mAdapter;
    private MainViewModel mViewModel;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }


    private void initViewModel() {

        final Observer<List<AssessmentEntity>> assessmentsObserver = new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(@Nullable List<AssessmentEntity> assessmentEntities) {
                allAssessments.clear();
                allAssessments.addAll(assessmentEntities);

                List<AssessmentEntity> selectedAssessments;
                selectedAssessments = getSelected(allAssessments);

                displayAssessments.clear();
                displayAssessments.addAll(selectedAssessments);

                if(mAdapter == null){
                    mAdapter = new AssessmentAdapter(displayAssessments, AssessmentDisplayActivity.this);
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

    private List<AssessmentEntity> getSelected(List<AssessmentEntity> all){
        Bundle extras = getIntent().getExtras();
        courseId = extras.getInt(COURSE_ID_KEY);
        Log.i("zzz", "getSelected in assessment display: cid is" + courseId);

        List<AssessmentEntity> selected = new ArrayList<>();
        for(int i = 0; i < allAssessments.size(); i++){
            AssessmentEntity assessment;
            assessment = allAssessments.get(i);

            int course = assessment.getCourseID();
            if(course == courseId){
                selected.add(assessment);
            }
        }
        return selected;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_back){
            Intent intent = new Intent(this, MentorDisplayActivity.class);
            //startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}