package com.sahilasopa.fitnesstracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.sahilasopa.fitnesstracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseDatabase database; // Firebase Database
    FirebaseAuth auth;
    Intent login;
    Intent calorieTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        login = new Intent(this, LoginActivity.class);
        calorieTracker = new Intent(this, CalorieActivity.class);
        if ((auth.getCurrentUser() == null)) {
            startActivity(login);
            finish();
        } // If User is Not logged in switch to login page
        binding.materialToolbar.setSubtitle("Fitness Tracker");
        binding.materialToolbar.setSubtitleTextColor(Color.WHITE);
        binding.materialToolbar.inflateMenu(R.menu.main);
        binding.materialToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.logout_menu) {
                auth.signOut();
                startActivity(login);
                finish();
            }
            return false;
        });
        binding.calorieTracker.setOnClickListener(view -> {
            startActivity(calorieTracker);
        });
    }
}