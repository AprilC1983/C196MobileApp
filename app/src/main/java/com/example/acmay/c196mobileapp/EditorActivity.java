package com.example.acmay.c196mobileapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.acmay.c196mobileapp.database.TermEntity;
import com.example.acmay.c196mobileapp.viewmodel.EditorViewModel;


import butterknife.BindView;
import butterknife.ButterKnife;

public class EditorActivity extends AppCompatActivity {

    @BindView(R.id.term_text)
    TextView mTextView;

    private EditorViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        initViewModel();
    }

    private void initViewModel(){
        mViewModel = ViewModelProviders.of(this)
                .get(EditorViewModel.class);

        mViewModel.mLiveTerm.observe(this, new Observer<TermEntity>() {
            @Override
            public void onChanged(@Nullable TermEntity termEntity) {
                if(termEntity != null) {
                    mTextView.setText(termEntity.getText());
                }
            }
        });
    }
}
