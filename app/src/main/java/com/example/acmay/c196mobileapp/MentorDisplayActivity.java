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
import com.example.acmay.c196mobileapp.database.MentorEntity;
import com.example.acmay.c196mobileapp.ui.MentorAdapter;
import com.example.acmay.c196mobileapp.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.acmay.c196mobileapp.utilities.Constants.COURSE_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.MENTOR_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.TERM_ID_KEY;

public class MentorDisplayActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static final String TAG = "Mentor Display";

    @OnClick(R.id.add_fab)
    void fabClickHandler(){
        Intent intent = new Intent(this, MentorEditorActivity.class);
        intent.putExtra(COURSE_ID_KEY, courseId);
        startActivity(intent);
        //Log.i("zz", "fabClickHandler mentor: cid is " + courseId);
    }


    private List<MentorEntity> allMentors = new ArrayList<>();
    private MentorAdapter mAdapter;
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


    //displays the selected mentor(s)
    private void initViewModel() {

        final Observer<List<MentorEntity>> mentorsObserver = new Observer<List<MentorEntity>>() {
            @Override
            public void onChanged(@Nullable List<MentorEntity> mentorEntities) {
                allMentors.clear();
                allMentors.addAll(mentorEntities);

                List<MentorEntity> selectedMentors;
                selectedMentors = getSelected(allMentors);

                if(mAdapter == null){
                    mAdapter = new MentorAdapter(selectedMentors, MentorDisplayActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                } else{
                    mAdapter.notifyDataSetChanged();
                }
            }
        };

        mViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);

        mViewModel.mMentors.observe(this, mentorsObserver);
    }


    //initializes the recyclerview
    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(
                mRecyclerView.getContext(), layoutManager.getOrientation());

        mRecyclerView.addItemDecoration(divider);

    }

    //returns a list of courses associated with the selected term
    private List<MentorEntity> getSelected(List<MentorEntity> all){
        Bundle extras = getIntent().getExtras();
        courseId = extras.getInt(COURSE_ID_KEY);
        Log.i("zz", "getSelected in mentor display cid is: " + courseId);

        List<MentorEntity> selected = new ArrayList<>();

        for(int i = 0; i < allMentors.size(); i++){
            MentorEntity mentor;
            mentor = allMentors.get(i);

            int course = mentor.getCourseID();
            if(course == courseId){
                selected.add(mentor);
            }
        }
        return selected;
    }
}