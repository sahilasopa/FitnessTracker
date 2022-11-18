package com.sahilasopa.fitnesstracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sahilasopa.fitnesstracker.R;
import com.sahilasopa.fitnesstracker.models.Exercise;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
    private final Context context;
    private final List<Exercise> exercises;

    public ExerciseAdapter(Context context, List<Exercise> Exercises) {
        this.context = context;
        this.exercises = Exercises;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.workout_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.name.setText(exercise.getName());
        holder.calorieBurn.setText(context.getString(R.string.calorie_burn, exercise.getCalBurn()));
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView calorieBurn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.exerciseName);
            calorieBurn = itemView.findViewById(R.id.calorieBurn);
            CardView cardView = itemView.findViewById(R.id.exerciseView);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAbsoluteAdapterPosition();
        }
    }
}
