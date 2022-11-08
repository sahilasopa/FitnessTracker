package com.sahilasopa.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.sahilasopa.fitnesstracker.databinding.ActivityFoodAddBinding;
import com.sahilasopa.fitnesstracker.utils.JsonParser;
import com.sahilasopa.fitnesstracker.utils.VolleyGetRequestUtil;
import com.sahilasopa.fitnesstracker.utils.VolleyListener;

public class FoodAddActivity extends AppCompatActivity implements VolleyListener {
    ActivityFoodAddBinding binding;
    FirebaseAuth auth;
    Intent login;
    VolleyGetRequestUtil getRequestUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        login = new Intent(this, LoginActivity.class);
        if ((auth.getCurrentUser() == null)) {
            startActivity(login);
        } // If User is Not logged in switch to login page
        getRequestUtil = new VolleyGetRequestUtil();
        binding.FindFood.setOnClickListener(view -> {
            String foodName = binding.foodName.getText().toString();
            if (foodName.isEmpty()) {
                binding.foodName.setError("Tell us what you had");
                binding.foodName.requestFocus();
                return;
            }
            getRequestUtil.getFoodInfo(foodName, this);
        });
    }

    @Override
    public void requestSuccess(String response) {
//        JsonParser
        System.out.println("Got this success response" + response);
    }

    @Override
    public void requestFailed(String response) {
        System.out.println("Got this error response" + response);
    }
}