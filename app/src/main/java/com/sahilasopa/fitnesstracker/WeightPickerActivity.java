package com.sahilasopa.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sahilasopa.fitnesstracker.databinding.ActivityWeightPickerBinding;

public class WeightPickerActivity extends AppCompatActivity {
    ActivityWeightPickerBinding binding;
    NumberPicker weightPicker;
    Intent heightPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeightPickerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        heightPicker = new Intent(this, HeightPickerActivity.class);
        weightPicker = binding.weightPicker;
        weightPicker.setMinValue(20);
        weightPicker.setMaxValue(200);
        binding.continueButton.setOnClickListener(view -> {
            int weight = weightPicker.getValue();
            heightPicker.putExtra("weight", weight);
            heightPicker.putExtra("gender", getIntent().getExtras().get("gender").toString());
            startActivity(heightPicker);
        });
        binding.backButton.setOnClickListener(view -> finish());
    }
}