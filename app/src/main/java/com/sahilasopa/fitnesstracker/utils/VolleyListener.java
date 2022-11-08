package com.sahilasopa.fitnesstracker.utils;

public interface VolleyListener {
    void requestSuccess(String response);

    void requestFailed(String response);
}
