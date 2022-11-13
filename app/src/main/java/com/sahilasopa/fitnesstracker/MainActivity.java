package com.sahilasopa.fitnesstracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.sahilasopa.fitnesstracker.databinding.ActivityMainBinding;
import com.sahilasopa.fitnesstracker.utils.AuthenticationVerifier;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseDatabase database; // Firebase Database
    FirebaseAuth auth;
    Intent login;
    AuthenticationVerifier authenticationVerifier;
    Intent calorieTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authenticationVerifier = new AuthenticationVerifier();
        authenticationVerifier.validateLogin(this);
        auth = FirebaseAuth.getInstance();
        login = new Intent(this, LoginActivity.class);
        calorieTracker = new Intent(this, CalorieActivity.class);
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