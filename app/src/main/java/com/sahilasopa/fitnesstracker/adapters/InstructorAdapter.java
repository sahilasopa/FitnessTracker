package com.sahilasopa.fitnesstracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahilasopa.fitnesstracker.R;
import com.sahilasopa.fitnesstracker.models.WorkoutInstructions;

import java.util.List;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.ViewHolder> {
    private final Context context;
    private final List<WorkoutInstructions> instructions;

    public InstructorAdapter(Context context, List<WorkoutInstructions> instructions) {
        this.context = context;
        this.instructions = instructions;
    }

    @NonNull
    @Override
    public InstructorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_instructor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructorAdapter.ViewHolder holder, int position) {
        WorkoutInstructions workoutInstruction = instructions.get(position);
        holder.name.setText(workoutInstruction.getName());
        holder.difficulty.setText(workoutInstruction.getDifficulty());
        holder.equipments.setText(String.format("Equipments: %s", workoutInstruction.getEquipment()));
        holder.descriptions.setText(workoutInstruction.getInstructions());
    }

    @Override
    public int getItemCount() {
        return instructions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView difficulty;
        public TextView equipments;
        public TextView descriptions;
        public Button more;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.instructorName);
            difficulty = itemView.findViewById(R.id.difficulty);
            equipments = itemView.findViewById(R.id.equipments);
            descriptions = itemView.findViewById(R.id.descriptions);
            more = itemView.findViewById(R.id.moreInfo);
            more.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (descriptions.getMaxLines() == 0) {
                descriptions.setMaxLines(Integer.MAX_VALUE);
                more.setText(R.string.less_info);
            } else {
                descriptions.setMaxLines(0);
                more.setText(R.string.more_info);
            }
        }
    }
}
