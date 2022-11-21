package com.sahilasopa.fitnesstracker;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.sahilasopa.fitnesstracker.databinding.ActivityWorkoutInstructorBinding;
import com.sahilasopa.fitnesstracker.models.DIFFICULTY;
import com.sahilasopa.fitnesstracker.models.MUSCLE;
import com.sahilasopa.fitnesstracker.models.TYPE;
import com.sahilasopa.fitnesstracker.utils.VolleyGetRequestUtil;
import com.sahilasopa.fitnesstracker.utils.VolleyListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WorkoutInstructorActivity extends AppCompatActivity implements VolleyListener {
    ActivityWorkoutInstructorBinding binding;
    VolleyGetRequestUtil getRequestUtil;
    JSONArray exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkoutInstructorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getRequestUtil = new VolleyGetRequestUtil();
        getRequestUtil.getExercise(this);
        binding.difficulty.setAdapter(new ArrayAdapter<>(this, R.layout.item, DIFFICULTY.values()));
        binding.type.setAdapter(new ArrayAdapter<>(this, R.layout.item, TYPE.values()));
        binding.muscle.setAdapter(new ArrayAdapter<>(this, R.layout.item, MUSCLE.values()));
    }

    @Override
    public void requestSuccess(String response) {
        System.out.println("Success Response: " + response);
        try {
            exercises = new JSONArray(response);
            for (int i = 0; i < exercises.length(); i++) {
                JSONObject exercise = exercises.getJSONObject(i);
                System.out.println(exercise);
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