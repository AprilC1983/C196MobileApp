package com.example.acmay.c196mobileapp;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.acmay.c196mobileapp.database.CourseEntity;
import com.example.acmay.c196mobileapp.viewmodel.CourseViewModel;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.acmay.c196mobileapp.utilities.Constants.CHANNEL_ID;
import static com.example.acmay.c196mobileapp.utilities.Constants.ALERT_MESSAGE_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.COURSE_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.EDITING_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.TERM_ID_KEY;

public class CourseEditorActivity extends AppCompatActivity {

    @BindView(R.id.course_text)
    TextView courseTextView;

    @BindView(R.id.course_start_picker)
    DatePicker courseStart;
    @BindView(R.id.course_end_picker)
    DatePicker courseEnd;

    @BindView(R.id.plan_to_take_rb)
    RadioButton plannedRb;
    @BindView(R.id.in_progress_rb)
    RadioButton inProgRb;
    @BindView(R.id.completed_rb)
    RadioButton completedRb;
    @BindView(R.id.dropped_rb)
    RadioButton droppedRb;

    //exits course screen without saving data
    @OnClick(R.id.course_cancel_btn)
    void cancelClickHandler(){
        finish();
    }

    //Saves course data without continuing to the assessment editor
    @OnClick(R.id.course_save_btn)
    void saveClickHandler(){
        saveAndReturn();
    }

    private CourseViewModel mViewModel;
    private boolean mNewCourse, mEditing;
    private int courseID;
    private int termID;
    private String planned = "Plan to take";
    private String taking = "In Progress";
    private String completed = "Completed";
    private String dropped = "Dropped";
    private String msgTxt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_editor);
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
                .get(CourseViewModel.class);

        mViewModel.mLiveCourse.observe(this, new Observer<CourseEntity>() {
            @Override
            public void onChanged(@Nullable CourseEntity courseEntity) {
                if(courseEntity != null && !mEditing) {
                    String currentStatus = courseEntity.getStatus();

                    if(currentStatus == planned){
                        plannedRb.setChecked(true);
                    }else if(currentStatus == taking){
                        inProgRb.setChecked(true);
                    }else if(currentStatus == completed){
                        completedRb.setChecked(true);
                    }else if(currentStatus == dropped){
                        droppedRb.setChecked(true);
                    }

                    courseTextView.setText(courseEntity.getTitle());
                }
            }
        });

        Bundle extras = getIntent().getExtras();

        if(extras == null){
            setTitle(R.string.new_course);
            mNewCourse = true;
        } else {
            setTitle(R.string.edit_course);
            courseID = extras.getInt(COURSE_ID_KEY);
            Log.i("cid", "course id = " + courseID);
            mViewModel.loadData(courseID);
            termID = extras.getInt(TERM_ID_KEY);
        }

    }

    //Creates a notification channel
    private void createAlert(long date, String courseMsg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

//Specifies which screen launches***********************************************************************8
            Intent intent = new Intent(this, AlertActivity.class);
            intent.putExtra(ALERT_MESSAGE_KEY, courseMsg);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

//This will trigger an alert for start and end dates
            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC, date, pendingIntent);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!mNewCourse){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_main, menu);
        }
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


    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        String title = courseTextView.getText().toString();
        String status = "";
        String startMessage;
        String endMessage;
        long startLong;
        long endLong;

        if(plannedRb.isChecked()){
            status = planned;
        }else if(inProgRb.isChecked()){
            status = taking;
        }else if(completedRb.isChecked()){
            status = completed;
        }else if(droppedRb.isChecked()){
            status = dropped;
        }

        int startDay = courseStart.getDayOfMonth();
        int startMonth = courseStart.getMonth();
        int startYear = courseStart.getYear();

        int endDay = courseEnd.getDayOfMonth();
        int endMonth = courseEnd.getMonth();
        int endYear = courseEnd.getYear();

        java.util.Date start = new java.util.Date(startYear - 1900, startMonth, startDay);
        java.util.Date end = new Date(endYear - 1900, endMonth, endDay);

        startLong = start.getTime();
        endLong = end.getTime();
        startMessage = title + " begins today";
        endMessage = title + " is ending today";

        createAlert(startLong, startMessage);
        createAlert(endLong, endMessage);

            mViewModel.saveCourse(termID, start, end, title, status);
            finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }


}