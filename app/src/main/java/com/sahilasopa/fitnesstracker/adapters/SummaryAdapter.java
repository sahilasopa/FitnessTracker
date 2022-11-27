package com.sahilasopa.fitnesstracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahilasopa.fitnesstracker.R;
import com.sahilasopa.fitnesstracker.models.Workout;

import java.util.List;
import java.util.Locale;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> {
    private final Context context;
    private final List<Workout> workouts;

    public SummaryAdapter(Context context, List<Workout> workouts) {
        this.context = context;
        this.workouts = workouts;
    }

    @NonNull
    @Override
    public SummaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_summary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryAdapter.ViewHolder holder, int position) {
        Workout workout = workouts.get(position);
        holder.timeSpent.setText(String.format(Locale.getDefault(), "Time Spent %.2f Hours", workout.getHoursSpent()));
        holder.caloriesBurned.setText(String.format(Locale.getDefault(), "Calories Burned %.2f KCAL", workout.getCaloriesBurned()));
        holder.name.setText(workout.getExercise().getName());
        holder.imageView.setImageResource(workout.getExercise().getLogoResource());
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView caloriesBurned;
        public TextView timeSpent;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.summeryExerciseName);
            caloriesBurned = itemView.findViewById(R.id.summaryCaloriesBurned);
            timeSpent = itemView.findViewById(R.id.timeSpent);
            imageView = itemView.findViewById(R.id.summeryExerciseLogo);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
