package com.sahilasopa.fitnesstracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahilasopa.fitnesstracker.R;
import com.sahilasopa.fitnesstracker.models.Exercise;

import java.util.List;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> {
    private final Context context;
    private final List<Exercise> exercise;

    public SummaryAdapter(Context context, List<Exercise> exercises) {
        this.context = context;
        this.exercise = exercises;
    }

    @NonNull
    @Override
    public SummaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.instructor_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryAdapter.ViewHolder holder, int position) {
        Exercise Exercise = exercise.get(position);
    }

    @Override
    public int getItemCount() {
        return exercise.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView difficulty;
        public TextView equipments;
        public TextView descriptions;
        public Button more;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.instructorName);
            difficulty = itemView.findViewById(R.id.difficulty);
            equipments = itemView.findViewById(R.id.equipments);
            descriptions = itemView.findViewById(R.id.descriptions);
            more = itemView.findViewById(R.id.moreInfo);
            more.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
