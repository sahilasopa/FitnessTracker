package com.sahilasopa.fitnesstracker;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sahilasopa.fitnesstracker.adapters.ExerciseAdapter;
import com.sahilasopa.fitnesstracker.databinding.ActivityExerciseBinding;
import com.sahilasopa.fitnesstracker.models.Exercise;

import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity {
    ActivityExerciseBinding binding;
    RecyclerView recyclerView;
    ExerciseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExerciseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerView = binding.recycleView;
        binding.materialToolbar2.setSubtitle("Workout Tracker");
        binding.materialToolbar2.setSubtitleTextColor(Color.WHITE);
        adapter = new ExerciseAdapter(this, createExercises());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Exercise> createExercises() {
        ArrayList<Exercise> exercises = new ArrayList<>();
        Exercise running = new Exercise("Running", 500, R.drawable.logo_running);
        Exercise walking = new Exercise("Walking", 250, R.drawable.logo_walking);
        Exercise cycling = new Exercise("Cycling", 600, R.drawable.logo_cycling);
        Exercise skating = new Exercise("Skating", 500, R.drawable.logo_skating);
        Exercise jogging = new Exercise("Jogging", 350, R.drawable.logo_jogging);
        Exercise swimming = new Exercise("Swimming", 450, R.drawable.logo_swimming);
        exercises.add(running);
        exercises.add(walking);
        exercises.add(cycling);
        exercises.add(skating);
        exercises.add(jogging);
        exercises.add(swimming);
        return exercises;
    }
}