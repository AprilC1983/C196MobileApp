package com.example.acmay.c196mobileapp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.acmay.c196mobileapp.database.TermEntity;
import com.example.acmay.c196mobileapp.notifications.NotificationPublisher;
import com.example.acmay.c196mobileapp.ui.TermAdapter;
import com.example.acmay.c196mobileapp.viewmodel.MainViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;
import static com.example.acmay.c196mobileapp.utilities.Constants.CHANNEL_ID;
import static com.example.acmay.c196mobileapp.utilities.Constants.COURSE_ALERT;
import static com.example.acmay.c196mobileapp.utilities.Constants.TERM_ID_KEY;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @OnClick(R.id.add_fab)
    void fabClickHandler(){
        Intent intent = new Intent(this, TermEditorActivity.class);
        intent.putExtra(TERM_ID_KEY, termId);
        startActivity(intent);
    }


    private List<TermEntity> termsData = new ArrayList<>();
    private TermAdapter mAdapter;
    private MainViewModel mViewModel;
    int termId;
    private String msgTxt = "A message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();

        createNotificationChannel();
    }

//***********************************************WORK WITH TERMS HERE*******************************************
    private void initViewModel() {

        final Observer<List<TermEntity>> termsObserver = new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(@Nullable List<TermEntity> termEntities) {
                termsData.clear();
                termsData.addAll(termEntities);

                for(int i = 0; i < termEntities.size(); i++){
                    Date start = termEntities.get(i).getStartDate();
                    Date end = termEntities.get(i).getEndDate();

                    long startNum = start.getTime();
                    long endNum = end.getTime();
                    //modify method to take long argument

                    //Log.i("yyyyy", "The difference is ");
                }

                if(mAdapter == null){
                    mAdapter = new TermAdapter(termsData, MainActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                } else{
                    mAdapter.notifyDataSetChanged();

                }
            }
        };

        mViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);

        mViewModel.mTerms.observe(this, termsObserver);

    }


    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(
                mRecyclerView.getContext(), layoutManager.getOrientation());

        mRecyclerView.addItemDecoration(divider);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //if(!mNewTerm){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_main, menu);
        //}
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

    //*************************************NOTIFICATION CODE****************************************

    /*
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(titleTxt)
            .setContentText(msgTxt)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);


     */

//Creates a notification channel
private void createNotificationChannel() {
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
        intent.putExtra(COURSE_ALERT, msgTxt);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

//This will trigger an alert for start and end dates
        Calendar calendar = Calendar.getInstance();
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC, calendar.getTimeInMillis() + 1000, pendingIntent);

        /*
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

         */

   //    notificationId is a unique int for each notification that you must define
        //notificationManager.notify(1, builder.build());

        }
    }


}

