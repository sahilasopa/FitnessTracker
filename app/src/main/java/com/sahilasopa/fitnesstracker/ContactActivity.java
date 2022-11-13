package com.sahilasopa.fitnesstracker;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.sahilasopa.fitnesstracker.databinding.ActivityContactBinding;
import com.sahilasopa.fitnesstracker.models.User;
import com.sahilasopa.fitnesstracker.utils.AuthenticationVerifier;

import java.util.Objects;

public class ContactActivity extends AppCompatActivity {
    ActivityContactBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseDatabase database;
    GoogleSignInClient mGoogleSignInClient; // SignIn Client For Google
    Intent home;
    Intent register;
    Intent login;
    Intent otp;
    AuthenticationVerifier authenticationVerifier;
    final int RC_SIGN_IN = 69;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authenticationVerifier = new AuthenticationVerifier();
        home = new Intent(this, MainActivity.class);
        register = new Intent(this, RegisterActivity.class);
        login = new Intent(this, LoginActivity.class);
        otp = new Intent(this, OtpActivity.class);
        auth = FirebaseAuth.getInstance();
        authenticationVerifier.validateLogin(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("One Time Password");
        binding.buttonSignIn.setOnClickListener(view -> {
            if (binding.username.getText().toString().isEmpty()) {
                binding.username.setError("This Field Is Required");
                binding.username.requestFocus();
                return;
            } else if (binding.contactNo.getText().toString().isEmpty()) {
                binding.contactNo.setError("This Field Is Required");
                binding.contactNo.requestFocus();
                return;
            }
            final String contactNo = binding.ccp.getSelectedCountryCodeWithPlus().concat(" ").concat(binding.contactNo.getText().toString());
            Log.v("response", contactNo);
            otp.putExtra("contact", contactNo);
            otp.putExtra("username", binding.username.getText().toString());
            startActivity(otp);
            finish();
        });
        binding.buttonEmail.setOnClickListener(view -> {
            startActivity(login);
            finish();
        });
        binding.buttonGoogle.setOnClickListener(view -> {
            googleSignIn();
            finish();
        });
        binding.register.setOnClickListener(view -> {
            startActivity(register);
            finish();
        });
    }

    public void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN); // Info: This method is deprecated but is still in use according to google documentation
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Google Sign in failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = auth.getCurrentUser();
                        User users = new User();
                        assert user != null;
                        users.setId(user.getUid());
                        users.setProfile_pic(Objects.requireNonNull(user.getPhotoUrl()).toString());
                        users.setUsername(user.getDisplayName());
                        database.getReference().child("Users").child(user.getUid()).setValue(users);
                        startActivity(home);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(ContactActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}