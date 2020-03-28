package com.example.oes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class McqAdapter extends RecyclerView.Adapter<McqAdapter.MyViewHolder> {
    private List<MCQ_CLASS> mcqList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView questionTag,questionText;
        RadioButton option1,option2,option3,option4;

        MyViewHolder(View view) {
           super(view);
           questionTag = view.findViewById(R.id.studentQuestionTag);
           questionText = view.findViewById(R.id.mcqQuestionText);
           option1 = view.findViewById(R.id.radioOption1);
           option2 = view.findViewById(R.id.radioOption2);
           option3 = view.findViewById(R.id.radioOption3);
           option4 = view.findViewById(R.id.radioOption4);
        }
    }


    McqAdapter(List<MCQ_CLASS> mcqList) {
        this.mcqList = mcqList;
    }

    @NonNull
    @Override
    public McqAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mcq_student_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull McqAdapter.MyViewHolder holder, int position) {
            MCQ_CLASS mcq_class = mcqList.get(position);
            holder.questionTag.setText(mcq_class.getQuestionTag());
            holder.questionText.setText(mcq_class.getQuestion());
            holder.option1.setText(mcq_class.getOption1());
            holder.option2.setText(mcq_class.getOption2());
            holder.option3.setText(mcq_class.getOption3());
            holder.option4.setText(mcq_class.getOption4());
        }

    @Override
    public int getItemCount() {
        return mcqList.size();
    }
}
