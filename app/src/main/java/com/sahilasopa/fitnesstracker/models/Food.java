package com.sahilasopa.fitnesstracker.models;

import androidx.annotation.NonNull;

import java.util.Date;

public class Food {
    private String name;
    private long calories;
    private long protein;
    private long fat;
    private Date timeStamp;
    private long carbohydrate;

    public Food(String name, long calories, long protein, long fat, long carbohydrate) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.timeStamp = new Date();
    }

    public Food() {
    }

    @NonNull
    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", calories=" + calories +
                ", protein=" + protein +
                ", fat=" + fat +
                ", timeStamp=" + timeStamp +
                ", carbohydrate=" + carbohydrate +
                '}';
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCalories() {
        return calories;
    }

    public void setCalories(long calories) {
        this.calories = calories;
    }

    public long getProtein() {
        return protein;
    }

    public void setProtein(long protein) {
        this.protein = protein;
    }

    public long getFat() {
        return fat;
    }

    public void setFat(long fat) {
        this.fat = fat;
    }

    public long getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(long carbohydrate) {
        this.carbohydrate = carbohydrate;
    }
}
