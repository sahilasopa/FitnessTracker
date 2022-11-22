package com.sahilasopa.fitnesstracker;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.sahilasopa.fitnesstracker.databinding.ActivityStopwatchBinding;
import com.sahilasopa.fitnesstracker.models.Exercise;
import com.sahilasopa.fitnesstracker.models.Workout;

import java.util.Locale;

public class StopwatchActivity extends AppCompatActivity {
    Intent home;
    int hrs;
    int mins;
    int secs;
    FirebaseAuth auth;
    FirebaseDatabase database;
    double hoursWorked;
    ActivityStopwatchBinding binding;
    private int sec = 0;
    private boolean is_running;
    private boolean was_running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStopwatchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        home = new Intent(this, MainActivity.class);
        if (savedInstanceState != null) {
            sec = savedInstanceState.getInt("seconds");
            is_running = savedInstanceState.getBoolean("running");
            was_running = savedInstanceState.getBoolean("wasRunning");
        }
        running_Timer();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", sec);
        savedInstanceState.putBoolean("running", is_running);
        savedInstanceState.putBoolean("wasRunning", was_running);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        was_running = is_running;
        is_running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (was_running) {
            is_running = true;
        }
    }

    public void onClickStart(View view) {
        if (is_running) {
            is_running = false;
            finishTimer();
            binding.startButton.setText(getString(R.string.start));
        } else {
            is_running = true;
            binding.startButton.setText(getString(R.string.stop));
        }
    }

    public void onClickReset(View view) {
        is_running = false;
        binding.startButton.setText(getString(R.string.start));
        sec = 0;
    }

    private void running_Timer() {
        final TextView t_View = findViewById(R.id.time_view);
        final Handler handle = new Handler();
        handle.post(new Runnable() {
            @Override
            public void run() {
                hrs = sec / 3600;
                mins = (sec % 3600) / 60;
                secs = sec % 60;
                String time_t = String.format(Locale.getDefault(), "    %d:%02d:%02d   ", hrs, mins, secs);
                t_View.setText(time_t);
                if (is_running) {
                    sec++;
                }
                handle.postDelayed(this, 1000);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishTimer();
    }

    private void finishTimer() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to end your timer?");
        builder.setTitle("Finish Exercise?!");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes, I am tired", (dialog, which) -> {
            endWorkout();
            startActivity(home);
            finish();
        });

        builder.setNegativeButton("No, I can do more", (dialog, which) -> dialog.cancel());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void endWorkout() {
        hoursWorked = hrs;
        hoursWorked += (mins / 60f);
        String result = String.format(Locale.ENGLISH, "%.2f", hoursWorked);
        Workout workout = new Workout((Exercise) getIntent().getExtras().get("exercise"), Double.parseDouble(result));
        Toast.makeText(this, "Burned: " + workout.getCaloriesBurned() + " Calories", Toast.LENGTH_SHORT).show();
        assert auth.getCurrentUser() != null;
        database.getReference().child("Users").child(auth.getCurrentUser().getUid()).child("workouts").child(String.valueOf(workout.getDate().getTime())).setValue(workout);
    }
}