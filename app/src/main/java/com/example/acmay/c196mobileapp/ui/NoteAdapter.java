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

import com.example.acmay.c196mobileapp.AssessmentDisplayActivity;
import com.example.acmay.c196mobileapp.CourseDetailActivity;
import com.example.acmay.c196mobileapp.NoteEditorActivity;
import com.example.acmay.c196mobileapp.MentorDisplayActivity;
import com.example.acmay.c196mobileapp.R;
import com.example.acmay.c196mobileapp.TermDetailActivity;
import com.example.acmay.c196mobileapp.database.NoteEntity;
import com.example.acmay.c196mobileapp.database.TermDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;
import static com.example.acmay.c196mobileapp.utilities.Constants.NOTE_ID_KEY;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private final List<NoteEntity> mNotes;
    private final Context mContext;

    public NoteAdapter(List<NoteEntity> mNotes, Context mContext) {
        this.mNotes = mNotes;
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
        final NoteEntity note = mNotes.get(position);
        holder.mTextView.setText(note.getText());

        holder.eFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, NoteEditorActivity.class);
                intent.putExtra(NOTE_ID_KEY, note.getId());
                mContext.startActivity(intent);
                Log.i(TAG, "onClick: Open Note editor");
            }
        });

        //This click listener will take user to the display of the assessments
        holder.cFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MentorDisplayActivity.class);
                intent.putExtra(NOTE_ID_KEY, note.getId());
                mContext.startActivity(intent);
                Log.i(TAG, "onClick: Open assessment display");
            }
        });
/*
        //Display the Note detail screen
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NoteDetailActivity.class);
                intent.putExtra(COURSE_ID_KEY, course.getId());
                mContext.startActivity(intent);
                Log.i(TAG, "onClick: Open course details");
            }
        });

 */


    }

    @Override
    public int getItemCount() {
        return mNotes.size();
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