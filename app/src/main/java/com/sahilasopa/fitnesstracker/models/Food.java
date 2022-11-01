package com.sahilasopa.fitnesstracker.models;

public class Food {
    String name;
    long calories;
    long protein;
    long fat;
    long carbohydrate;
    long fiber;
    long quantity;

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", calories=" + calories +
                ", protein=" + protein +
                ", fat=" + fat +
                ", carbohydrate=" + carbohydrate +
                ", fiber=" + fiber +
                ", quantity=" + quantity +
                '}';
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

    public long getFiber() {
        return fiber;
    }

    public void setFiber(long fiber) {
        this.fiber = fiber;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
