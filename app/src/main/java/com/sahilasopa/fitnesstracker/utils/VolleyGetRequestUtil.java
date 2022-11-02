package com.sahilasopa.fitnesstracker.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
}
