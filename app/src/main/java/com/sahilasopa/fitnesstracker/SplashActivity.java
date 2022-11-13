package com.sahilasopa.fitnesstracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final int delayMillis = 3000;
        new Handler().postDelayed(() -> {
            Intent main = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(main);
            finish();
        }, delayMillis);
    }
}