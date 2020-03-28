package com.example.oes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BlanksAdapter extends RecyclerView.Adapter<BlanksAdapter.MyViewHolder> {
    private List<BLANKS_CLASS> blankList;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView questionTag,questionText;

        MyViewHolder(View view) {
            super(view);
            questionTag = view.findViewById(R.id.studentQuestionTag);
            questionText = view.findViewById(R.id.blankQuestionText);
        }
    }


    BlanksAdapter(List<BLANKS_CLASS> blankList) {
        this.blankList = blankList;
    }

    @NonNull
    @Override
    public BlanksAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blanks_student_layout, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull BlanksAdapter.MyViewHolder holder, int position) {
        BLANKS_CLASS blanks_class = blankList.get(position);
        holder.questionTag.setText(blanks_class.getQuestionTag());
        holder.questionText.setText(blanks_class.getQuestion());
    }

    @Override
    public int getItemCount() {
        return blankList.size();
    }
}
