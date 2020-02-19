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
import com.example.acmay.c196mobileapp.database.MentorEntity;
import com.example.acmay.c196mobileapp.database.NoteEntity;
import com.example.acmay.c196mobileapp.ui.MentorAdapter;
import com.example.acmay.c196mobileapp.ui.NoteAdapter;
import com.example.acmay.c196mobileapp.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.acmay.c196mobileapp.utilities.Constants.COURSE_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.MENTOR_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.TERM_ID_KEY;

public class NoteDisplayActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    @OnClick(R.id.add_fab)
    void fabClickHandler(){
        Intent intent = new Intent(this, NoteEditorActivity.class);
        intent.putExtra(COURSE_ID_KEY, courseId);
        startActivity(intent);
        Log.i("ndis", "fabClickHandler mentor: cid is " + courseId);
    }


    private List<NoteEntity> allNotes = new ArrayList<>();
    private List<NoteEntity> displayNotes = new ArrayList<>();
    private NoteAdapter mAdapter;
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


    //displays the selected Note(s)
    private void initViewModel() {

        final Observer<List<NoteEntity>> notesObserver = new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(@Nullable List<NoteEntity> noteEntities) {
                allNotes.clear();
                allNotes.addAll(noteEntities);

                List<NoteEntity> selectedNotes;
                selectedNotes = getSelected(allNotes);

                displayNotes.clear();
                displayNotes.addAll(selectedNotes);

                if(mAdapter == null){
                    mAdapter = new NoteAdapter(displayNotes, NoteDisplayActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                } else{
                    mAdapter.notifyDataSetChanged();
                }
            }
        };

        mViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);

        mViewModel.mNotes.observe(this, notesObserver);
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
    private List<NoteEntity> getSelected(List<NoteEntity> all){
        Bundle extras = getIntent().getExtras();
        courseId = extras.getInt(COURSE_ID_KEY);

        List<NoteEntity> selected = new ArrayList<>();

        for(int i = 0; i < allNotes.size(); i++){
            NoteEntity note;
            note = allNotes.get(i);

            int course = note.getCourseID();
            if(course == courseId){
                selected.add(note);
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