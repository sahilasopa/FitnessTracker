package com.sahilasopa.fitnesstracker;

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
import com.sahilasopa.fitnesstracker.databinding.ActivityLoginBinding;
import com.sahilasopa.fitnesstracker.models.User;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseDatabase database;
    GoogleSignInClient mGoogleSignInClient; // SignIn Client For Google
    Intent completeProfile;
    Intent register;
    Intent contact;
    final int RC_SIGN_IN = 69;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        completeProfile = new Intent(this, CompleteProfileActivity.class);
        register = new Intent(this, RegisterActivity.class);
        contact = new Intent(this, ContactActivity.class);
        completeProfile = new Intent(this, CompleteProfileActivity.class);
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        if ((auth.getCurrentUser() != null)) {
            startActivity(completeProfile);
        } // If User is logged in switch to completeProfile page
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Login to your account");
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        binding.buttonGoogle.setOnClickListener(v -> signIn());
        binding.buttonSignIn.setOnClickListener(view -> {
            if (binding.email.getText().toString().isEmpty()) {
                binding.email.setError("This Field Is Required");
                binding.email.requestFocus();
                return;
            } else if (binding.password.getText().toString().isEmpty()) {
                binding.password.setError("This Field Is Required");
                binding.password.requestFocus();
                return;
            }
            progressDialog.show();
            auth.signInWithEmailAndPassword(binding.email.getText().toString(), binding.password.getText().toString()).addOnCompleteListener(task -> {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    completeProfile.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(completeProfile);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
        binding.buttonMobileNo.setOnClickListener(view -> startActivity(contact));
        binding.alreadyHaveAccountText.setOnClickListener(v -> startActivity(register));
    }

    public void signIn() {
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
                Log.d("response", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("response", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithCredential:success");
                        FirebaseUser user = auth.getCurrentUser();
                        User users = new User();
                        assert user != null;
                        users.setId(user.getUid());
                        users.setProfile_pic(Objects.requireNonNull(user.getPhotoUrl()).toString());
                        users.setUsername(user.getDisplayName());
                        database.getReference().child("Users").child(user.getUid()).setValue(users);
                        startActivity(completeProfile);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithCredential:failure", task.getException());
                    }
                });
    }
}