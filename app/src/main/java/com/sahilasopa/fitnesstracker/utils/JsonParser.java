package com.sahilasopa.fitnesstracker.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

    public void findFood(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONObject nutrients = new JSONObject(jsonObject.getJSONArray("parsed").getJSONObject(0).getJSONObject("food").getJSONObject("nutrients").toString());
        System.out.println("nutrients are: " + nutrients);
    }
}

