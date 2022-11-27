package com.sahilasopa.fitnesstracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sahilasopa.fitnesstracker.databinding.ActivityCalorieBinding;
import com.sahilasopa.fitnesstracker.models.Food;

import java.util.Locale;

public class CalorieActivity extends AppCompatActivity {
    ActivityCalorieBinding binding;
    Intent addFood;
    long totalCal;
    long totalFats;
    long totalCarbs;
    long totalProtein;
    FirebaseDatabase database;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalorieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.materialToolbar.setSubtitle("Calorie Tracker");
        binding.materialToolbar.setSubtitleTextColor(Color.WHITE);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        addFood = new Intent(this, FoodAddActivity.class);
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        database.getReference().child("Users").child(user.getUid()).child("food").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalProtein = 0;
                totalFats = 0;
                totalCal = 0;
                totalCarbs = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Food food = ds.getValue(Food.class);
                    assert food != null;
                    totalCal += food.getCalories();
                    totalFats += food.getFat();
                    totalCarbs += food.getCarbohydrate();
                    totalProtein += food.getProtein();
                    binding.calorieCount.setText(String.format(Locale.getDefault(), "%s KCAL", totalCal));
                    binding.fatCount.setText(String.format(Locale.getDefault(), "%s g", totalFats));
                    binding.carbsCount.setText(String.format(Locale.getDefault(), "%s g", totalCarbs));
                    binding.proteinCount.setText(String.format(Locale.getDefault(), "%s g", totalProtein));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.addFood.setOnClickListener(binding -> startActivity(addFood));
    }
}