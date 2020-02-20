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

import com.example.acmay.c196mobileapp.database.CourseEntity;
import com.example.acmay.c196mobileapp.ui.CourseAdapter;
import com.example.acmay.c196mobileapp.viewmodel.MainViewModel;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.acmay.c196mobileapp.utilities.Constants.TERM_ID_KEY;

public class CourseDisplayActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static final String TAG = "Course Display";

    @OnClick(R.id.add_fab)
    void fabClickHandler(){
        Intent intent = new Intent(this, CourseEditorActivity.class);
        intent.putExtra(TERM_ID_KEY, termId);
        startActivity(intent);
        Log.i(TAG, "fabClickHandler: create course");
    }

    private List<CourseEntity> allCourses = new ArrayList<>();
    private List<CourseEntity> displayCourses = new ArrayList<>();
    private CourseAdapter mAdapter;
    private MainViewModel mViewModel;

    private int termId;

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



    ///////////////////////////////////////////kjcnvkdfnkxfnd
    private void initViewModel() {

        final Observer<List<CourseEntity>> coursesObserver = new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(@Nullable List<CourseEntity> courseEntities) {
                allCourses.clear();
                allCourses.addAll(courseEntities);

                List<CourseEntity> selectedCourses;
                selectedCourses = getSelected(allCourses);

                displayCourses.clear();
                displayCourses.addAll(selectedCourses);

                if(mAdapter == null){
                    mAdapter = new CourseAdapter(displayCourses, CourseDisplayActivity.this);
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
    ////////////////////////////////////////////////////////////////////////////////////////////////////











    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(
                mRecyclerView.getContext(), layoutManager.getOrientation());

        mRecyclerView.addItemDecoration(divider);

    }

    //returns a list of courses associated with the selected term
    private List<CourseEntity> getSelected(List<CourseEntity> all){
        Bundle extras = getIntent().getExtras();
        termId = extras.getInt(TERM_ID_KEY);

        List<CourseEntity> selected = new ArrayList<>();
        for(int i = 0; i < allCourses.size(); i++){
            CourseEntity course;
            course = allCourses.get(i);

            int term = course.getTermID();
            if(term == termId){
                selected.add(course);
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
        /*
        if(item.getItemId() == android.R.id.home){
            saveAndReturn();
            return true;
        } else if(item.getItemId() == R.id.action_delete){
            mViewModel.deleteCourse();
            finish();
        }

         */
        return super.onOptionsItemSelected(item);
    }
}