package com.example.civicnodeapplication.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;
import com.example.civicnodeapplication.dashboard.UserDashboardActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.*;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextInput, editTextOtp;
    private Button buttonSendOtp, buttonVerifyOtp, btnLogin;
    private RadioGroup radioGroupLoginType;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private String verificationId;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Linking UI Elements
        editTextInput = findViewById(R.id.editTextInput);
        editTextOtp = findViewById(R.id.editTextOtp);
        buttonSendOtp = findViewById(R.id.buttonSendOtp);
        buttonVerifyOtp = findViewById(R.id.buttonVerifyOtp);
        btnLogin = findViewById(R.id.btnLogin);
        radioGroupLoginType = findViewById(R.id.radioGroupLoginType);
        progressBar = findViewById(R.id.progressBar);

        // Default visibility
        editTextOtp.setVisibility(View.GONE);
        buttonVerifyOtp.setVisibility(View.GONE);
        btnLogin.setEnabled(false);

        // Login Type Toggle
        radioGroupLoginType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioMobile) {
                editTextInput.setHint("Enter Mobile Number");
                editTextInput.setInputType(android.text.InputType.TYPE_CLASS_PHONE);
            } else {
                editTextInput.setHint("Enter Email");
                editTextInput.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            }
        });

        // Send OTP
        buttonSendOtp.setOnClickListener(v -> sendOtp());

        // Verify OTP
        buttonVerifyOtp.setOnClickListener(v -> verifyOtp());

        // Login Button
        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, UserDashboardActivity.class));
            finish();
        });
    }

    private void sendOtp() {
        String phoneNumber = editTextInput.getText().toString().trim();

        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 10) {
            editTextInput.setError("Enter valid mobile number");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                                progressBar.setVisibility(View.GONE);
                                signInWithCredential(credential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Verification Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId,
                                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                progressBar.setVisibility(View.GONE);
                                LoginActivity.this.verificationId = verificationId;
                                resendToken = token;
                                editTextOtp.setVisibility(View.VISIBLE);
                                buttonVerifyOtp.setVisibility(View.VISIBLE);
                                Toast.makeText(LoginActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyOtp() {
        String otp = editTextOtp.getText().toString().trim();
        if (TextUtils.isEmpty(otp) || otp.length() < 6) {
            editTextOtp.setError("Enter valid OTP");
            return;
        }

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "OTP Verified", Toast.LENGTH_SHORT).show();
                        btnLogin.setEnabled(true);
                    } else {
                        Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
