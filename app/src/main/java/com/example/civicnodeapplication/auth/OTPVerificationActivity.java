package com.example.civicnodeapplication.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.civicnodeapplication.MainActivity;
import com.example.civicnodeapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPVerificationActivity extends AppCompatActivity {

    private EditText otpInput;
    private Button verifyOtpButton, resendOtpButton;
    private TextView countdownTimerText, phoneNumberText;
    private ProgressBar progressBar;

    private String verificationId;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private String phoneNumber;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        mAuth = FirebaseAuth.getInstance();

        otpInput = findViewById(R.id.otp_input);
        verifyOtpButton = findViewById(R.id.verify_otp_button);
        resendOtpButton = findViewById(R.id.resend_otp_button);
        countdownTimerText = findViewById(R.id.countdown_timer);
        phoneNumberText = findViewById(R.id.phone_number_text);
        progressBar = findViewById(R.id.progress_bar);

        phoneNumber = getIntent().getStringExtra("phone_number");
        verificationId = getIntent().getStringExtra("verification_id");

        phoneNumberText.setText("OTP sent to: " + phoneNumber);
        startCountdownTimer();

        verifyOtpButton.setOnClickListener(v -> {
            String otp = otpInput.getText().toString().trim();
            if (TextUtils.isEmpty(otp) || otp.length() < 6) {
                otpInput.setError("Enter valid OTP");
                return;
            }
            verifyOtp(otp);
        });

        resendOtpButton.setOnClickListener(v -> resendOtp());
    }

    private void verifyOtp(String otp) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(OTPVerificationActivity.this, "Verification Successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OTPVerificationActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(OTPVerificationActivity.this, "Invalid OTP. Try Again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void resendOtp() {
        resendOtpButton.setEnabled(false);
        startCountdownTimer();

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                        Toast.makeText(OTPVerificationActivity.this, "OTP Auto-Detected!", Toast.LENGTH_SHORT).show();
                        verifyOtp(credential.getSmsCode());
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(OTPVerificationActivity.this, "Failed to resend OTP: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        resendOtpButton.setEnabled(true);
                    }

                    @Override
                    public void onCodeSent(@NonNull String newVerificationId,
                                           @NonNull PhoneAuthProvider.ForceResendingToken newToken) {
                        verificationId = newVerificationId;
                        resendToken = newToken;
                        Toast.makeText(OTPVerificationActivity.this, "OTP Sent Again!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setForceResendingToken(resendToken)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void startCountdownTimer() {
        resendOtpButton.setEnabled(false);
        countDownTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                countdownTimerText.setText("Resend OTP in: " + millisUntilFinished / 1000 + "s");
            }
            public void onFinish() {
                resendOtpButton.setEnabled(true);
                countdownTimerText.setText("Didn't receive OTP? Resend Now");
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
