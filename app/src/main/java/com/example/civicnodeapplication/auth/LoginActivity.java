package com.example.civicnodeapplication.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.civicnodeapplication.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private EditText inputField, otpCode;
    private Button sendOtpBtn, verifyOtpBtn;
    private RadioGroup loginTypeGroup;
    private RadioButton mobileRadio, emailRadio;
    private FirebaseAuth mAuth;
    private String verificationId;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        inputField = findViewById(R.id.editTextInput);
        otpCode = findViewById(R.id.editTextOtp);
        sendOtpBtn = findViewById(R.id.buttonSendOtp);
        verifyOtpBtn = findViewById(R.id.buttonVerifyOtp);
        loginTypeGroup = findViewById(R.id.radioGroupLoginType);
        mobileRadio = findViewById(R.id.radioMobile);
        emailRadio = findViewById(R.id.radioEmail);
        progressBar = findViewById(R.id.progressBar);

        loginTypeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            inputField.setHint(checkedId == R.id.radioMobile ? "Enter Mobile Number" : "Enter Email Address");
        });

        sendOtpBtn.setOnClickListener(v -> {
            String phoneNumber = inputField.getText().toString().trim();
            if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 10) {
                inputField.setError("Enter a valid phone number");
                return;
            }
            sendOtp(phoneNumber);
        });

        verifyOtpBtn.setOnClickListener(v -> {
            String otp = otpCode.getText().toString().trim();
            if (TextUtils.isEmpty(otp) || otp.length() < 6) {
                otpCode.setError("Enter a valid OTP");
                return;
            }
            verifyOtp(otp);
        });
    }

    private void sendOtp(String phoneNumber) {
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
                                Toast.makeText(LoginActivity.this, "Verification Successful", Toast.LENGTH_SHORT).show();
                                signInWithCredential(credential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Verification Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                LoginActivity.this.verificationId = verificationId;
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyOtp(String otp) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
