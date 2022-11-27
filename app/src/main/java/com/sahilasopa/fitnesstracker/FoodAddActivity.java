package com.sahilasopa.fitnesstracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.sahilasopa.fitnesstracker.databinding.ActivityFoodAddBinding;
import com.sahilasopa.fitnesstracker.models.Food;
import com.sahilasopa.fitnesstracker.utils.JsonParser;
import com.sahilasopa.fitnesstracker.utils.VolleyGetRequestUtil;
import com.sahilasopa.fitnesstracker.utils.VolleyListener;

import org.json.JSONObject;


public class FoodAddActivity extends AppCompatActivity implements VolleyListener {
    ActivityFoodAddBinding binding;
    FirebaseAuth auth;
    VolleyGetRequestUtil getRequestUtil;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        getRequestUtil = new VolleyGetRequestUtil();
        database = FirebaseDatabase.getInstance();
        binding.materialToolbar.setSubtitle("Calorie Tracker");
        binding.materialToolbar.setSubtitleTextColor(Color.WHITE);
        binding.FindFood.setOnClickListener(view -> {
            String foodName = binding.foodName.getText().toString();
            if (foodName.isEmpty()) {
                binding.foodName.setError("Tell us what you had");
                binding.foodName.requestFocus();
                return;
            }
            getRequestUtil.getFoodInfo(foodName, this);
        });
        binding.manually.setOnClickListener(view -> startActivity(new Intent(this, ManualFoodAddActivity.class)));
    }

    @Override
    public void requestSuccess(String response) {
        JsonParser jsonParser = new JsonParser();
        try {
            JSONObject n = new JSONObject(jsonParser.findFood(response));
            String calorie = n.get("ENERC_KCAL").toString().split("\\.")[0];
            String protein = n.get("PROCNT").toString().split("\\.")[0];
            String carbs = n.get("CHOCDF").toString().split("\\.")[0];
            String fats = n.get("FAT").toString().split("\\.")[0];
            String foodName = binding.foodName.getText().toString();
            displayNutrients(calorie, fats, protein, carbs);
            binding.save.setEnabled(true);
            binding.save.setOnClickListener(view -> {
                Food food = new Food(foodName,
                        Integer.parseInt(calorie),
                        Integer.parseInt(protein),
                        Integer.parseInt(fats),
                        Integer.parseInt(carbs)
                );
                FirebaseUser user = auth.getCurrentUser();
                assert user != null;
                database.getReference().child("Users").child(user.getUid()).child("food").child(String.valueOf(food.getTimeStamp().getTime())).setValue(food);
                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(this, "Saved To Database", Toast.LENGTH_SHORT).show();
                finish();
            });
        } catch (Exception e) {
            Toast.makeText(this, "Food not found in database", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        System.out.println("Got this success response" + response);
    }

    private void displayNutrients(String calorie, String fats, String protein, String carbs) {
        binding.calorieCount.setText(getString(R.string.calorie_kcal, calorie));
        binding.fatCount.setText(getString(R.string.fat_grams, fats));
        binding.proteinCount.setText(getString(R.string.protein_grams, protein));
        binding.carbsCount.setText(getString(R.string.carbs_grams, carbs));
    }

    @Override
    public void requestFailed(String response) {
        System.out.println("Got this error response" + response);
    }
}