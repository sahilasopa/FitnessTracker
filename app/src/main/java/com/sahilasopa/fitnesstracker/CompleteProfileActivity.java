package com.sahilasopa.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sahilasopa.fitnesstracker.databinding.ActivityCompleteProfileBinding;
import com.sahilasopa.fitnesstracker.models.User;

import java.util.HashMap;
import java.util.Map;

public class CompleteProfileActivity extends AppCompatActivity {
    ActivityCompleteProfileBinding binding;
    FirebaseDatabase database; // Firebase Database
    FirebaseAuth auth;
    Intent login;
    Intent home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCompleteProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        login = new Intent(this, LoginActivity.class);
        home = new Intent(this, MainActivity.class);
        if ((auth.getCurrentUser() == null)) {
            startActivity(login);
            finish();
        } // If User is Not logged in switch to login page
        database.getReference("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    assert user != null;
                    if (auth.getCurrentUser().getUid().equals(user.getId())) {
                        System.out.println(user);
                        binding.age.setText(String.valueOf(user.getAge()));
                        binding.weight.setText(String.valueOf(user.getWeight()));
                        binding.height.setText(String.valueOf(user.getHeight()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Pass
            }
        });
        binding.buttonCompleteProfile.setOnClickListener(v -> {
            EditText age = binding.age;
            EditText weight = binding.weight;
            EditText height = binding.height;
            if (age.getText().toString().isEmpty()) {
                age.setError("This Field Is Required");
                age.requestFocus();
                return;
            } else if (weight.getText().toString().isEmpty()) {
                weight.setError("This Field Is Required");
                weight.requestFocus();
                return;
            } else if (height.getText().toString().isEmpty()) {
                height.setError("This Field Is Required");
                height.requestFocus();
                return;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("age", Integer.valueOf(binding.age.getText().toString()));
            map.put("weight", Integer.valueOf(binding.weight.getText().toString()));
            map.put("height", Integer.valueOf(binding.height.getText().toString()));
            database.getReference("Users").child(auth.getCurrentUser().getUid()).updateChildren(map);
            startActivity(home);
            finish();
        });
    }
}