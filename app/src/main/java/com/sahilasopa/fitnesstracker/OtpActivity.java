package com.sahilasopa.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.sahilasopa.fitnesstracker.databinding.ActivityOtpBinding;
import com.sahilasopa.fitnesstracker.models.User;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {
    final int RC_SIGN_IN = 69;
    ActivityOtpBinding binding;
    String contact;
    String username;
    FirebaseDatabase database;
    PhoneAuthProvider.ForceResendingToken resendToken;
    FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient; // SignIn Client For Google
    Intent completeProfile;
    Intent register;
    Intent login;
    Intent email;
    private String verificationId;
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode(); // This is called if phone is able to verify code without user input (auto verification)
            if (code != null) {
                verifyCode(code);
            }
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            resendToken = forceResendingToken;
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.v("response", e.getMessage());
        }
    };

    // Contact Verification Functions Start

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        completeProfile = new Intent(this, GenderSelectActivity.class);
        register = new Intent(this, RegisterActivity.class);
        email = new Intent(this, LoginActivity.class);
        login = new Intent(this, OtpActivity.class);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        if ((auth.getCurrentUser() != null)) {
            startActivity(completeProfile);
            finish();
        } // If User is logged in switch to completeProfile page
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        contact = getIntent().getExtras().get("contact").toString();
        username = getIntent().getExtras().get("username").toString();
        binding.title.setText(getString(R.string.enter_the_otp).concat(contact));
        sendVerificationCode(contact);
        binding.buttonVerify.setOnClickListener(view -> {
            if (binding.otp.getText().toString().isEmpty() || binding.otp.getText().toString().length() < 6) {
                binding.otp.setError("Enter a valid otp");
                binding.otp.requestFocus();
                return;
            }
            verifyCode(binding.otp.getText().toString());
        });
        binding.buttonEmail.setOnClickListener(view -> {

        });
        binding.resend.setOnClickListener(view -> sendVerificationCode(contact));
        binding.buttonGoogle.setOnClickListener(view -> googleSignIn());
        binding.register.setOnClickListener(view -> {
            startActivity(register);
            finish();
        });
    }

    public void verifyCode(String code) {
        // This function is used to verify code
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        // This is used to verify code by user input
        auth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String id = Objects.requireNonNull(task.getResult().getUser()).getUid();
                User users = new User();
                users.setContact_no(contact);
                users.setUsername(username);
                users.setId(id);
                database.getReference().child("Users").child(id).setValue(users);
                startActivity(completeProfile);
                finish();
            } else {
                Log.v("response", Objects.requireNonNull(task.getException()).toString());
                if (task.getException().toString().contains("com.google.firebase.auth.FirebaseAuthInvalidCredentialsException")) {
                    Toast.makeText(this, "Invalid Verification Code", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void sendVerificationCode(String contact_no) {
        // As the name says its used to send verification code xD
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(contact_no)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // Contact Verification Functions End

    // Google Login Functions Start

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
                        startActivity(completeProfile);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(OtpActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Google Login functions end
}