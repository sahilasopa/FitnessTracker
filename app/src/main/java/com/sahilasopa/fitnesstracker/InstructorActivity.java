package com.sahilasopa.fitnesstracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sahilasopa.fitnesstracker.adapters.InstructorAdapter;
import com.sahilasopa.fitnesstracker.databinding.ActivityInstructorBinding;
import com.sahilasopa.fitnesstracker.models.WorkoutInstructions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InstructorActivity extends AppCompatActivity {
    ActivityInstructorBinding binding;
    JSONArray array;
    RecyclerView recyclerView;
    InstructorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInstructorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerView = binding.recycleView;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        try {
            List<WorkoutInstructions> workoutInstructions = new ArrayList<>();
            array = new JSONArray(getIntent().getExtras().get("array").toString());
            for (int i = 0; i < array.length(); i++) {
                boolean contains = false;
                JSONObject object = array.getJSONObject(i);
                WorkoutInstructions instructions = new WorkoutInstructions(object.getString("name"), object.getString("type"), object.getString("muscle"), object.getString("equipment"), object.getString("difficulty"), object.getString("instructions"));
                for (WorkoutInstructions workoutInstruction : workoutInstructions) {
                    if (workoutInstruction.getName().equals(instructions.getName())) {
                        contains = true;
                        break;
                    }
                }
                if (!contains){
                    workoutInstructions.add(instructions);
                }
            }
            System.out.println(workoutInstructions);
            adapter = new InstructorAdapter(this, workoutInstructions);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}