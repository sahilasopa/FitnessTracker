package com.sahilasopa.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sahilasopa.fitnesstracker.databinding.ActivityGenderSelectBinding;

public class GenderSelectActivity extends AppCompatActivity {
    ActivityGenderSelectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGenderSelectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}