package com.sahilasopa.fitnesstracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
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
import com.sahilasopa.fitnesstracker.databinding.ActivityRegisterBinding;
import com.sahilasopa.fitnesstracker.models.User;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    final int RC_SIGN_IN = 69; // This Is Needed To Verify Google SignIn
    ActivityRegisterBinding binding; // Layout Binding
    FirebaseDatabase database; // Firebase Database
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    GoogleSignInClient mGoogleSignInClient; // SignInClient For Google Auth
    Intent completeProfile;
    Intent login;
    Intent contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        completeProfile = new Intent(this, GenderSelectActivity.class);
        login = new Intent(this, LoginActivity.class);
        contact = new Intent(this, ContactActivity.class);
        auth = FirebaseAuth.getInstance();
        if ((auth.getCurrentUser() != null)) {
            startActivity(completeProfile);
            finish();
        } // If User is logged in switch to completeProfile page
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("we're creating your account");
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        binding.buttonSignIn.setOnClickListener(v -> {
            EditText username = binding.username;
            EditText email = binding.email;
            EditText password = binding.password;
            if (username.getText().toString().isEmpty()) {
                username.setError("This Field Is Required");
                username.requestFocus();
                return;
            } else if (email.getText().toString().isEmpty()) {
                email.setError("This Field Is Required");
                email.requestFocus();
                return;
            } else if (password.getText().toString().isEmpty()) {
                password.setError("This Field Is Required");
                password.requestFocus();
                return;
            }
            progressDialog.show();
            auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(task -> {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    User user = new User(username.getText().toString(), email.getText().toString(), password.getText().toString());
                    String id = Objects.requireNonNull(task.getResult().getUser()).getUid();
                    user.setId(id);
                    user.setPassword("");
                    database.getReference().child("Users").child(id).setValue(user);
                    startActivity(completeProfile);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
        binding.buttonGoogle.setOnClickListener(view -> googleSignIn());
        binding.buttonMobileNo.setOnClickListener(view -> {
            startActivity(contact);
            finish();
        });
        binding.alreadyHaveAccountText.setOnClickListener(v -> {
            startActivity(login);
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
                Log.v("response", String.valueOf(e.getStatus()));
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
                        startActivity(completeProfile);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(RegisterActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}