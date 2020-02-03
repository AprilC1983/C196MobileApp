package com.example.acmay.c196mobileapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acmay.c196mobileapp.R;
import com.example.acmay.c196mobileapp.database.TermEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.ViewHolder> {

    private final List<TermEntity> mCourses;
    private final Context mContext;

    public TermsAdapter(List<TermEntity> mCourses, Context mContext) {
        this.mCourses = mCourses;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.course_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final TermEntity course = mCourses.get(position);
        viewHolder.mTextView.setText(course.getText());
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.course_text)
        TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
