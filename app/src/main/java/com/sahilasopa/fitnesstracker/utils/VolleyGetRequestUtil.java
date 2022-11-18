package com.sahilasopa.fitnesstracker.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class VolleyGetRequestUtil {
    RequestQueue queue;
    VolleyListener volleyListener;

    public void getFoodInfo(String foodName, Context context) {
        int APP_ID = 85742922;
        queue = Volley.newRequestQueue(context);
        String APP_KEY = "93efa8d438c36e0a261c681b3e3da0d8";
        volleyListener = (VolleyListener) context;
        String URL = "https://api.edamam.com/api/food-database/v2/parser";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, String.format(URL + "?app_id=%s&app_key=%s&ingr=%s", APP_ID, APP_KEY, foodName),
                response -> volleyListener.requestSuccess(response),
                error -> volleyListener.requestFailed(error.getMessage()));
        queue.add(stringRequest);
    }

    public void getExercise(Context context) {
        queue = Volley.newRequestQueue(context);
        String URL = "https://api.api-ninjas.com/v1/exercises";
        String API_KEY = "ST5l8tPFnc0oC/ga4MxfjQ==Whb393F90dPAHa6E";
        volleyListener = (VolleyListener) context;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                response -> volleyListener.requestSuccess(response),
                error -> volleyListener.requestFailed(error.getMessage())
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("X-Api-Key", API_KEY);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
