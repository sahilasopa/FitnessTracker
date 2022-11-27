package com.sahilasopa.fitnesstracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sahilasopa.fitnesstracker.adapters.SummaryAdapter;
import com.sahilasopa.fitnesstracker.databinding.ActivityStatisticsBinding;
import com.sahilasopa.fitnesstracker.models.Workout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class StatisticsActivity extends AppCompatActivity {
    ActivityStatisticsBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    RecyclerView recyclerView;
    SummaryAdapter adapter;
    Integer pushupsCount;
    Integer squatsCount;
    List<Workout> workoutArray;
    DatabaseReference pushups;
    DatabaseReference squats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatisticsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        pushups = database.getReference().child("Users")
                .child(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                .child("Ml Workout")
                .child("pushups_down");
        squats = database.getReference().child("Users")
                .child(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                .child("Ml Workout")
                .child("squats_down");
        pushups.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pushupsCount = snapshot.getValue(Integer.class);
                if (pushupsCount == null) {
                    pushupsCount = 0;
                }
                binding.pushupsCount.setText(String.format(Locale.getDefault(), "%d Push-Ups", pushupsCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        squats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                squatsCount = snapshot.getValue(Integer.class);
                if (squatsCount == null) {
                    squatsCount = 0;
                }
                binding.squatsCount.setText(String.format(Locale.getDefault(), "%d Squats", squatsCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView = binding.recycleView;
        workoutArray = new ArrayList<>();
        assert auth.getCurrentUser() != null;
        System.out.println("Came till here");
        database.getReference().child("Users").child(auth.getCurrentUser().getUid()).child("workouts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Workout workout = ds.getValue(Workout.class);
                    assert workout != null;
                    if (workout.getCaloriesBurned() > 0)
                        workoutArray.add(workout);
                }
                adapter = new SummaryAdapter(getApplicationContext(), workoutArray);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error is " + error);
            }
        });
    }
}