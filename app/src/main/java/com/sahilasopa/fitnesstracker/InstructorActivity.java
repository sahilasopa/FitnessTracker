package com.sahilasopa.fitnesstracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sahilasopa.fitnesstracker.databinding.ActivityInstructorBinding;

import org.json.JSONArray;
import org.json.JSONException;

public class InstructorActivity extends AppCompatActivity {
    ActivityInstructorBinding binding;
    JSONArray array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInstructorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try {
            array = new JSONArray(getIntent().getExtras().get("array"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}