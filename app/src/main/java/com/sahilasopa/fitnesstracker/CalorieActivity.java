package com.sahilasopa.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sahilasopa.fitnesstracker.databinding.ActivityCalorieBinding;
import com.sahilasopa.fitnesstracker.utils.AuthenticationVerifier;

public class CalorieActivity extends AppCompatActivity {
    ActivityCalorieBinding binding;
    Intent addFood;
    AuthenticationVerifier authenticationVerifier;


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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        authenticationVerifier.validateLogin(this);
        super.onCreate(savedInstanceState, persistentState);
    }
}