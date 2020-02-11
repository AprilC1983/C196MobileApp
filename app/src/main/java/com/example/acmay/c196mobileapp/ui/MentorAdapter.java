package com.example.acmay.c196mobileapp.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acmay.c196mobileapp.CourseDisplayActivity;
import com.example.acmay.c196mobileapp.MentorEditorActivity;
import com.example.acmay.c196mobileapp.R;
import com.example.acmay.c196mobileapp.database.MentorEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;
import static com.example.acmay.c196mobileapp.utilities.Constants.MENTOR_ID_KEY;

public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.ViewHolder> {

    private final List<MentorEntity> mMentors;
    private final Context mContext;

    public MentorAdapter(List<MentorEntity> mMentors, Context mContext) {
        this.mMentors = mMentors;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MentorEntity mentor = mMentors.get(position);
        holder.mTextView.setText(mentor.getName());

        holder.eFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MentorEditorActivity.class);
                intent.putExtra(MENTOR_ID_KEY, mentor.getId());
                mContext.startActivity(intent);
                Log.i(TAG, "onClick: Open Mentor editor");
            }
        });

        holder.cFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CourseDisplayActivity.class);
                intent.putExtra(MENTOR_ID_KEY, mentor.getId());
                mContext.startActivity(intent);
                Log.i(TAG, "onClick: open courses display");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMentors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.display_text)
        TextView mTextView;
        @BindView(R.id.edit_fab)
        FloatingActionButton eFab;
        @BindView(R.id.continue_fab)
        FloatingActionButton cFab;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}