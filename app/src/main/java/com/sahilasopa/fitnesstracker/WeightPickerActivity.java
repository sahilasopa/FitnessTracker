package com.sahilasopa.fitnesstracker;

import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

import com.sahilasopa.fitnesstracker.databinding.ActivityWeightPickerBinding;

public class WeightPickerActivity extends AppCompatActivity {
    ActivityWeightPickerBinding binding;
    NumberPicker weightPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeightPickerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        weightPicker = binding.weightPicker;
        weightPicker.setMinValue(20);
        weightPicker.setMaxValue(200);

        binding.continueButton.setOnClickListener(view -> {
            int age = weightPicker.getValue();
        });
    }
}