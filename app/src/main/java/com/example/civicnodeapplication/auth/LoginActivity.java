package com.example.civicnodeapplication.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private EditText inputField, otpCode;
    private Button sendOtpBtn, verifyOtpBtn;
    private RadioGroup loginTypeGroup;
    private RadioButton mobileRadio, emailRadio;

    private FirebaseAuth mAuth;
    private String verificationId;
    private ProgressDialog loadingBar;

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
        loadingBar = new ProgressDialog(this);

        sendOtpBtn.setOnClickListener(v -> {
            String input = inputField.getText().toString().trim();
            if (TextUtils.isEmpty(input)) {
                inputField.setError("This field cannot be empty");
                return;
            }
            if (mobileRadio.isChecked()) {
                if (input.length() < 10) {
                    inputField.setError("Enter a valid phone number");
                    return;
                }
                sendMobileOtp("+91" + input);
            } else if (emailRadio.isChecked()) {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
                    inputField.setError("Enter a valid email address");
                    return;
                }
                sendEmailOtp(input);
            }
        });

        verifyOtpBtn.setOnClickListener(v -> {
            String code = otpCode.getText().toString().trim();
            if (TextUtils.isEmpty(code)) {
                otpCode.setError("Enter OTP");
                return;
            }
            verifyMobileOtp(code);
        });

        loginTypeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            otpCode.setVisibility(View.GONE);
            verifyOtpBtn.setVisibility(View.GONE);
            inputField.setText("");
            inputField.setHint(checkedId == R.id.radioMobile ? "Enter Mobile Number" : "Enter Email Address");
        });
    }

    private void sendMobileOtp(String phoneNumber) {
        loadingBar.setMessage("Sending OTP...");
        loadingBar.show();

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                                signInWithCredential(credential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                loadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "Verification Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("LoginActivity", "Verification Failed", e);
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                loadingBar.dismiss();
                                verificationId = s;
                                otpCode.setVisibility(View.VISIBLE);
                                verifyOtpBtn.setVisibility(View.VISIBLE);
                                Toast.makeText(LoginActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyMobileOtp(String code) {
        loadingBar.setMessage("Verifying OTP...");
        loadingBar.show();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    loadingBar.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendEmailOtp(String email) {
        loadingBar.setMessage("Checking Email...");
        loadingBar.show();

        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
            loadingBar.dismiss();
            if (task.isSuccessful()) {
                SignInMethodQueryResult result = task.getResult();
                if (result.getSignInMethods().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "No account found! Please register first.", Toast.LENGTH_LONG).show();
                } else {
                    sendEmailSignInLink(email);
                }
            } else {
                Toast.makeText(LoginActivity.this, "Error checking email", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendEmailSignInLink(String email) {
        loadingBar.setMessage("Sending Email OTP...");
        loadingBar.show();

        mAuth.sendSignInLinkToEmail(email, FirebaseAuth.getInstance().getActionCodeSettings())
                .addOnCompleteListener(task -> {
                    loadingBar.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Email verification link sent!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Failed to send link: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
