package com.sahilasopa.fitnesstracker.models;

import androidx.annotation.NonNull;

import java.util.Locale;

public enum DIFFICULTY {
    beginner,
    intermediate,
    expert;

    @NonNull
    @Override
    public String toString() {
        return super.toString().substring(0, 1).toUpperCase(Locale.ROOT) + super.toString().substring(1).toLowerCase(Locale.ROOT);
    }
}