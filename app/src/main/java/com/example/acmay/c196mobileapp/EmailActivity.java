package com.example.acmay.c196mobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.acmay.c196mobileapp.utilities.Constants.ASS_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.COURSE_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.EMAIL_ID;
import static com.example.acmay.c196mobileapp.utilities.Constants.MESSAGE_ID;
import static com.example.acmay.c196mobileapp.utilities.Constants.SUBJECT_ID;

public class EmailActivity extends AppCompatActivity {

    @BindView(R.id.recipientTxt)
    EditText recipTxt;
    @BindView(R.id.subjectTxt)
    EditText subTxt;
    @BindView(R.id.messageTxt)
    EditText msgTxt;

    @OnClick(R.id.sendBtn)
    void sendClickHandler(){
        String message = msgTxt.getText().toString();
        String recipient = recipTxt.getText().toString();
        String subject = subTxt.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(EMAIL_ID, recipient);
        intent.putExtra(SUBJECT_ID, subject);
        intent.putExtra(MESSAGE_ID, message);
        intent.setType("message/rfc822");

        startActivity(Intent.createChooser(intent,"Choose Mail App"));
        Log.i("bats", "send: button was pressed");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_layout);

        Bundle extras = getIntent().getExtras();

         if(extras != null) {
            String note = extras.getString(MESSAGE_ID);
            //msgTxt.setText(note);
        }else if(extras == null){
             Log.i("email", "onCreate: extras was null");
         }
    }
}