package com.sahilasopa.fitnesstracker;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.sahilasopa.fitnesstracker.databinding.ActivityFoodAddBinding;
import com.sahilasopa.fitnesstracker.utils.AuthenticationVerifier;
import com.sahilasopa.fitnesstracker.utils.VolleyGetRequestUtil;
import com.sahilasopa.fitnesstracker.utils.VolleyListener;

public class FoodAddActivity extends AppCompatActivity implements VolleyListener {
    ActivityFoodAddBinding binding;
    FirebaseAuth auth;
    VolleyGetRequestUtil getRequestUtil;
    AuthenticationVerifier authenticationVerifier;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        authenticationVerifier.validateLogin(this);
        super.onCreate(savedInstanceState, persistentState);
    }
}