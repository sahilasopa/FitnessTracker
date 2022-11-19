package com.sahilasopa.fitnesstracker.models;

public class MlWorkout {
    private String workoutName;
    private int reps;

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }


    public MlWorkout(String workoutName, int reps) {
        this.workoutName = workoutName;
        this.reps = reps;
    }
}
