package com.sahilasopa.fitnesstracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahilasopa.fitnesstracker.R;
import com.sahilasopa.fitnesstracker.StopwatchActivity;
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
        View view = LayoutInflater.from(context).inflate(R.layout.row_workout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.name.setText(exercise.getName());
        holder.calorieBurn.setText(context.getString(R.string.caloriesZ_burn, exercise.getCalBurn()));
        holder.imageView.setImageResource(exercise.getLogoResource());
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView calorieBurn;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.exerciseName);
            calorieBurn = itemView.findViewById(R.id.calorieBurn);
            Button start = itemView.findViewById(R.id.start);
            imageView = itemView.findViewById(R.id.exerciseLogo);
            start.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAbsoluteAdapterPosition();
            Exercise exercise = exercises.get(pos);
            Intent stopwatch = new Intent(context, StopwatchActivity.class);
            stopwatch.putExtra("exercise", exercise);
            context.startActivity(stopwatch);
        }
    }
}
