package com.sahilasopa.fitnesstracker.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.sahilasopa.fitnesstracker.LoginActivity;

public class AuthenticationVerifier {
    FirebaseAuth auth;
    Intent login;

    public void validateLogin(Context context) {
        auth = FirebaseAuth.getInstance();
        new Intent(context, LoginActivity.class);
        if ((auth.getCurrentUser() == null)) {
            context.startActivity(login);
            ((Activity) context).finish();
        } // If User is Not logged in switch to login page
    }
}
