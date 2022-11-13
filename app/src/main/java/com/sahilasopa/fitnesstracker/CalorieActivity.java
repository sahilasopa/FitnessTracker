package com.sahilasopa.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sahilasopa.fitnesstracker.databinding.ActivityCalorieBinding;

public class CalorieActivity extends AppCompatActivity {
    ActivityCalorieBinding binding;
    Intent addFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalorieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addFood = new Intent(this, FoodAddActivity.class);
        binding.addFood.setOnClickListener(binding -> {
            startActivity(addFood);
        });
    }
}