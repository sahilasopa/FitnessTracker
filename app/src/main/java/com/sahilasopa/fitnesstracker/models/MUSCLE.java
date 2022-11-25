package com.sahilasopa.fitnesstracker.models;

import androidx.annotation.NonNull;

import java.util.Locale;

public enum MUSCLE {
    muscle,
    biceps,
    abdominals,
    abductors,
    adductors,
    calves,
    chest,
    forearms,
    glutes,
    hamstrings,
    lats,
    lower_back,
    middle_back,
    neck,
    quadriceps,
    traps,
    triceps;

    @NonNull
    @Override
    public String toString() {
        return super.toString().substring(0, 1).toUpperCase(Locale.ROOT) + super.toString().substring(1).toLowerCase(Locale.ROOT).replace("_", " ");
    }
}
