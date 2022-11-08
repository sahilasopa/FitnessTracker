package com.sahilasopa.fitnesstracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sahilasopa.fitnesstracker.databinding.ActivityHeightPickerBinding;

public class HeightPickerActivity extends AppCompatActivity {
    ActivityHeightPickerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHeightPickerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.heightPicker.setMinValue(100);
        binding.heightPicker.setMaxValue(210);
    }
}