package com.sahilasopa.fitnesstracker.models;

import java.io.Serializable;

public class Exercise implements Serializable {
    private String name;
    private long calBurn;
    private DIFFICULTY difficulty;
    private MUSCLE muscle;
    private TYPE type;
    private int logoResource;

    public int getLogoResource() {
        return logoResource;
    }

    public void setLogoResource(int logoResource) {
        this.logoResource = logoResource;
    }

    public Exercise(String name, long calBurn, DIFFICULTY difficulty, MUSCLE muscle, TYPE type) {
        this.name = name;
        this.calBurn = calBurn;
        this.difficulty = difficulty;
        this.muscle = muscle;
        this.type = type;
    }

    public Exercise(String name, long calBurn, int logoResource) {
        this.name = name;
        this.calBurn = calBurn;
        this.logoResource = logoResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCalBurn() {
        return calBurn;
    }

    public void setCalBurn(long calBurn) {
        this.calBurn = calBurn;
    }

    public DIFFICULTY getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(DIFFICULTY difficulty) {
        this.difficulty = difficulty;
    }

    public MUSCLE getMuscle() {
        return muscle;
    }

    public void setMuscle(MUSCLE muscle) {
        this.muscle = muscle;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + name + '\'' +
                ", calBurn=" + calBurn +
                ", difficulty=" + difficulty +
                ", muscle=" + muscle +
                ", type=" + type +
                '}';
    }
}
