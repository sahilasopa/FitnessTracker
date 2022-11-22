package com.sahilasopa.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sahilasopa.fitnesstracker.databinding.ActivityWorkoutInstructorBinding;
import com.sahilasopa.fitnesstracker.models.DIFFICULTY;
import com.sahilasopa.fitnesstracker.models.MUSCLE;
import com.sahilasopa.fitnesstracker.models.TYPE;
import com.sahilasopa.fitnesstracker.utils.VolleyGetRequestUtil;
import com.sahilasopa.fitnesstracker.utils.VolleyListener;

import org.json.JSONArray;
import org.json.JSONException;

public class WorkoutInstructorActivity extends AppCompatActivity implements VolleyListener {
    ActivityWorkoutInstructorBinding binding;
    VolleyGetRequestUtil getRequestUtil;
    JSONArray exercises;
    String params = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkoutInstructorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getRequestUtil = new VolleyGetRequestUtil();
        binding.difficulty.setAdapter(new ArrayAdapter<>(this, R.layout.item, DIFFICULTY.values()));
        binding.type.setAdapter(new ArrayAdapter<>(this, R.layout.item, TYPE.values()));
        binding.muscle.setAdapter(new ArrayAdapter<>(this, R.layout.item, MUSCLE.values()));
        binding.find.setOnClickListener(view -> {
            params = "";
            if (binding.exerciseName.getText().toString().isEmpty()) {
                getDropdownOptions(binding.difficulty, binding.type, binding.muscle);
            } else {
                params = ("?name=" + binding.exerciseName.getText().toString());
            }
            getRequestUtil.getExercise(this, params);
        });
    }

    private void getDropdownOptions(Spinner difficulty, Spinner type, Spinner muscle) {
        params += "?difficulty=" + (difficulty.getSelectedItem().toString());
        params += "&type=" + (type.getSelectedItem().toString());
        params += "&muscle=" + (muscle.getSelectedItem().toString());
    }

    @Override
    public void requestSuccess(String response) {
        System.out.println("Success Response: " + response);
        try {
            exercises = new JSONArray(response);
            if (exercises.length() == 0) {
                Toast.makeText(this, "No Such Exercise found", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, InstructorActivity.class).putExtra("array", String.valueOf(exercises)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestFailed(String response) {
        System.out.println("Oops Error Response: " + response);
    }
}