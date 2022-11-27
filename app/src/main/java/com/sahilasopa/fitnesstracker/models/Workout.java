package com.sahilasopa.fitnesstracker.models;

import java.util.Date;

public class Workout {
    private Exercise exercise;
    private Date date;
    private double caloriesBurned;
    private double hoursSpent;

    public Workout(Exercise exercise, double hoursSpent) {
        this.exercise = exercise;
        this.date = new Date();
        this.caloriesBurned = exercise.getCalBurn() * hoursSpent;
        this.hoursSpent = hoursSpent;
    }

    public Workout() {
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "exercise=" + exercise +
                ", date=" + date +
                ", caloriesBurned=" + caloriesBurned +
                ", hoursSpent=" + hoursSpent +
                '}';
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public double getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(double hoursSpent) {
        this.hoursSpent = hoursSpent;
    }
}
