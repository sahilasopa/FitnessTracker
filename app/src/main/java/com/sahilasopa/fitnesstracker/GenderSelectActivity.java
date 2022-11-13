package com.sahilasopa.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sahilasopa.fitnesstracker.databinding.ActivityGenderSelectBinding;
import com.sahilasopa.fitnesstracker.utils.AuthenticationVerifier;

public class GenderSelectActivity extends AppCompatActivity {
    ActivityGenderSelectBinding binding;
    Intent weightPicker;
    AuthenticationVerifier authenticationVerifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGenderSelectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authenticationVerifier = new AuthenticationVerifier();
        weightPicker = new Intent(this, WeightPickerActivity.class);
        binding.buttonMale.setOnClickListener(view -> {
            weightPicker.putExtra("gender", "Male");
            startActivity(weightPicker);
        });
        binding.buttonFemale.setOnClickListener(view -> {
            weightPicker.putExtra("gender", "Female");
            startActivity(weightPicker);
        });
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        authenticationVerifier.validateLogin(this);
        super.onCreate(savedInstanceState, persistentState);
    }
}