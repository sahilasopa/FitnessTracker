package com.sahilasopa.fitnesstracker;

import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

import com.sahilasopa.fitnesstracker.databinding.ActivityAgePickerBinding;

public class AgePickerActivity extends AppCompatActivity {
    ActivityAgePickerBinding binding;
    NumberPicker agePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAgePickerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        agePicker = binding.agePicker;
        agePicker.setMinValue(3);
        agePicker.setMaxValue(100);

        binding.continueButton.setOnClickListener(view -> {
            int age = agePicker.getValue();
        });
    }
}