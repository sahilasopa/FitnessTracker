package com.sahilasopa.fitnesstracker;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.sahilasopa.fitnesstracker.databinding.ActivityFoodAddBinding;
import com.sahilasopa.fitnesstracker.utils.JsonParser;
import com.sahilasopa.fitnesstracker.utils.VolleyGetRequestUtil;
import com.sahilasopa.fitnesstracker.utils.VolleyListener;

import org.json.JSONException;

public class FoodAddActivity extends AppCompatActivity implements VolleyListener {
    ActivityFoodAddBinding binding;
    FirebaseAuth auth;
    VolleyGetRequestUtil getRequestUtil;


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
        JsonParser jsonParser = new JsonParser();
        try {
            jsonParser.findFood(response);
        } catch (JSONException e) {
            // food not in database
            Toast.makeText(this, "Food not found in database", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        System.out.println("Got this success response" + response);
    }

    @Override
    public void requestFailed(String response) {
        System.out.println("Got this error response" + response);
    }
}