package com.example.oes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {
    private List<Score> requestedResultList;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView rollText, marksText;

        MyViewHolder(View view) {
            super(view);
            rollText = view.findViewById(R.id.resultRollText);
            marksText = view.findViewById(R.id.resultMarksText);
        }
    }


    ResultAdapter(List<Score> requestedResultList) {
        this.requestedResultList = requestedResultList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.score_stats_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Score currScore = requestedResultList.get(position);
        holder.rollText.setText(currScore.getRoll());
        holder.marksText.setText(currScore.getMarks());
    }

    @Override
    public int getItemCount() {
        return requestedResultList.size();
    }
}
