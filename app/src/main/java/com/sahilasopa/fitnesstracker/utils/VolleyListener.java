package com.sahilasopa.fitnesstracker.utils;

public interface VolleyListener {
    String requestSuccess(String response);

    String requestFailed(String response);
}
