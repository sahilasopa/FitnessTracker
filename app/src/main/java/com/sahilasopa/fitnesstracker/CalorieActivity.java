package com.sahilasopa.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.sahilasopa.fitnesstracker.databinding.ActivityCalorieBinding;

public class CalorieActivity extends AppCompatActivity {
    ActivityCalorieBinding binding;
    FirebaseAuth auth;
    Intent login;
    Intent addFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalorieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        addFood = new Intent(this, FoodAddActivity.class);
        login = new Intent(this, LoginActivity.class);
        if ((auth.getCurrentUser() == null)) {
            startActivity(login);
        } // If User is Not logged in switch to login page
        binding.addFood.setOnClickListener(binding -> {
            startActivity(addFood);
        });
    }
}