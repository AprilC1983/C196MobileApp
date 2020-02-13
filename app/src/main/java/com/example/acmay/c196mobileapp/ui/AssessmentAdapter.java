package com.example.acmay.c196mobileapp.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acmay.c196mobileapp.AssessmentDetailActivity;
import com.example.acmay.c196mobileapp.AssessmentEditorActivity;
import com.example.acmay.c196mobileapp.R;
import com.example.acmay.c196mobileapp.database.AssessmentEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.acmay.c196mobileapp.utilities.Constants.ASS_ID_KEY;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.ViewHolder> {

    private final List<AssessmentEntity> mAssessments;
    private final Context mContext;

    public AssessmentAdapter(List<AssessmentEntity> mAssessments, Context mContext) {
        this.mAssessments = mAssessments;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final AssessmentEntity assessment = mAssessments.get(position);
        holder.mTextView.setText(assessment.getText());

        //open assessment editor
        holder.eFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AssessmentEditorActivity.class);
                intent.putExtra(ASS_ID_KEY, assessment.getId());
                mContext.startActivity(intent);
            }
        });

        //display assessment details
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AssessmentDetailActivity.class);
                intent.putExtra(ASS_ID_KEY, assessment.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAssessments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.display_text_1)
        TextView mTextView;
        @BindView(R.id.edit_fab_1)
        FloatingActionButton eFab;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
