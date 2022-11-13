package com.sahilasopa.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.sahilasopa.fitnesstracker.databinding.ActivityHeightPickerBinding;
import com.sahilasopa.fitnesstracker.utils.AuthenticationVerifier;

import java.util.HashMap;
import java.util.Map;

public class HeightPickerActivity extends AppCompatActivity {
    ActivityHeightPickerBinding binding;
    NumberPicker heightPicker;
    FirebaseDatabase database; // Firebase Database
    FirebaseAuth auth;
    Intent home;
    AuthenticationVerifier verifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHeightPickerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        heightPicker = binding.heightPicker;
        verifier = new AuthenticationVerifier();
        home = new Intent(this, MainActivity.class);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        heightPicker.setMinValue(20);
        heightPicker.setMaxValue(200);
        binding.continueButton.setOnClickListener(view -> {
            int height = heightPicker.getValue();
            int weight = (int) getIntent().getExtras().get("weight");
            String gender = getIntent().getExtras().get("gender").toString();
            assert auth.getCurrentUser() != null;
            Map<String, Object> map = new HashMap<>();
            map.put("gender", gender);
            map.put("weight", weight);
            map.put("height", height);
            database.getReference("Users").child(auth.getCurrentUser().getUid()).updateChildren(map);
            startActivity(home);
            finish();
        });
        binding.backButton.setOnClickListener(view -> finish());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        verifier.validateLogin(this);
        super.onCreate(savedInstanceState, persistentState);
    }
}