package com.example.civicnodeapplication.auth;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

public class OTPVerificationActivity extends AppCompatActivity {
    private EditText otpInput;
    private Button verifyOtpButton, resendOtpButton;
    private ProgressBar progressBar;
    private TextView countdownTimer, phoneNumberText;
    private String verificationId;
    private PhoneAuthProvider.ForceResendingToken resendingToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        otpInput = findViewById(R.id.otp_input);
        verifyOtpButton = findViewById(R.id.verify_otp_button);
        resendOtpButton = findViewById(R.id.resend_otp_button);
        progressBar = findViewById(R.id.progress_bar);
        countdownTimer = findViewById(R.id.countdown_timer);
        phoneNumberText = findViewById(R.id.phone_number_text);

        String phoneNumber = getIntent().getStringExtra("phone_number");
        phoneNumberText.setText("OTP sent to: " + phoneNumber);

        sendOtp(phoneNumber);

        verifyOtpButton.setOnClickListener(v -> verifyOtp());
        resendOtpButton.setOnClickListener(v -> resendOtp(phoneNumber));
    }

    private void sendOtp(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder()
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyOtp() {
        String code = otpInput.getText().toString().trim();
        if (code.isEmpty() || code.length() < 6) {
            otpInput.setError("Enter valid OTP");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        // Implement sign-in logic
    }

    private void resendOtp(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder()
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callbacks)
                .setForceResendingToken(resendingToken)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                    signInWithCredential(credential);
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(OTPVerificationActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                    OTPVerificationActivity.this.verificationId = verificationId;
                    resendingToken = token;
                }
            };
}
