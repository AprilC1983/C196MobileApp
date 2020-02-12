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
import com.example.acmay.c196mobileapp.MentorDisplayActivity;
import com.example.acmay.c196mobileapp.TermDetailActivity;
import com.example.acmay.c196mobileapp.TermEditorActivity;
import com.example.acmay.c196mobileapp.R;
import com.example.acmay.c196mobileapp.database.TermEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;
import static com.example.acmay.c196mobileapp.utilities.Constants.COURSE_ID_KEY;
import static com.example.acmay.c196mobileapp.utilities.Constants.TERM_ID_KEY;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {

    private final List<TermEntity> mTerms;
    private final Context mContext;

    public TermAdapter(List<TermEntity> mTerms, Context mContext) {
        this.mTerms = mTerms;
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
        final TermEntity term = mTerms.get(position);
        holder.mTextView.setText(term.getTitle());

        holder.eFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TermEditorActivity.class);
                intent.putExtra(TERM_ID_KEY, term.getId());
                mContext.startActivity(intent);
                Log.i(TAG, "onClick: Open term editor");
            }
        });

        holder.cFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CourseDisplayActivity.class);
                intent.putExtra(TERM_ID_KEY, term.getId());
                mContext.startActivity(intent);
                Log.i(TAG, "onClick: open mentors display");
            }
        });

        //display term detail screen
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TermDetailActivity.class);
                intent.putExtra(COURSE_ID_KEY, term.getId());
                mContext.startActivity(intent);
                Log.i(TAG, "onClick: Open term details");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTerms.size();
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