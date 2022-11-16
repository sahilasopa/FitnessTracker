package com.sahilasopa.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.sahilasopa.fitnesstracker.databinding.ActivityManualFoodAddBinding;
import com.sahilasopa.fitnesstracker.models.Food;

public class ManualFoodAddActivity extends AppCompatActivity {
    ActivityManualFoodAddBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManualFoodAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        binding.buttonAddFood.setOnClickListener(view -> {
            if (binding.carbs.getText().toString().isEmpty()) {
                handleEmpty(binding.carbs, "Please Enter Carbs Consumed in Grams");
                return;
            } else if (binding.protein.getText().toString().isEmpty()) {
                handleEmpty(binding.protein, "Please Enter Protein Consumed in Grams");
                return;
            } else if (binding.fat.getText().toString().isEmpty()) {
                handleEmpty(binding.fat, "Please Enter Fat Consumed in Grams");
                return;
            } else if (binding.calorie.getText().toString().isEmpty()) {
                handleEmpty(binding.calorie, "Please Enter Calorie Consumed in KCAL");
                return;
            } else if (binding.foodName.getText().toString().isEmpty()) {
                handleEmpty(binding.foodName, "Please Enter The Food Name");
                return;
            }
            Food food = new Food(
                    binding.foodName.getText().toString(),
                    Integer.parseInt(binding.calorie.getText().toString()),
                    Integer.parseInt(binding.protein.getText().toString()),
                    Integer.parseInt(binding.fat.getText().toString()),
                    Integer.parseInt(binding.carbs.getText().toString()
                    )
            );
            FirebaseUser user = auth.getCurrentUser();
            assert user != null;
            database.getReference().child("Users").child(user.getUid()).child("food").child(String.valueOf(food.getTimeStamp().getTime())).setValue(food);
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this, "Saved To Database", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void handleEmpty(EditText editText, String errorMessage) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError(errorMessage);
            editText.requestFocus();
        }
    }
}