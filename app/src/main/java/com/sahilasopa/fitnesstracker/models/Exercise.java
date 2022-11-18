package com.sahilasopa.fitnesstracker.models;

public class Exercise {
    private String name;
    private long calBurn;
    private DIFFICULTY difficulty;
    private MUSCLE muscle;
    private TYPE type;

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

    public Exercise(String name, long calBurn, DIFFICULTY difficulty, MUSCLE muscle, TYPE type) {
        this.name = name;
        this.calBurn = calBurn;
        this.difficulty = difficulty;
        this.muscle = muscle;
        this.type = type;
    }

    public Exercise(String name, long calBurn) {
        this.name = name;
        this.calBurn = calBurn;
    }
}
