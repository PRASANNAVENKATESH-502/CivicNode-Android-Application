package com.example.civicnodeapplication.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText nameInput, emailInput, phoneInput, passwordInput;
    private Button registerButton;
    private ProgressBar progressBar;
    private RadioGroup authMethodRadioGroup;

    private FirebaseAuth mAuth;
    private String selectedAuthMethod = "email";  // Default to email authentication

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        nameInput = findViewById(R.id.name_input);
        emailInput = findViewById(R.id.email_input);
        phoneInput = findViewById(R.id.phone_input);
        passwordInput = findViewById(R.id.password_input);
        registerButton = findViewById(R.id.register_button);
        progressBar = findViewById(R.id.progress_bar);
        authMethodRadioGroup = findViewById(R.id.auth_method_group);

        authMethodRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_email) {
                selectedAuthMethod = "email";
                emailInput.setVisibility(View.VISIBLE);
                passwordInput.setVisibility(View.VISIBLE);
                phoneInput.setVisibility(View.GONE);
            } else if (checkedId == R.id.radio_phone) {
                selectedAuthMethod = "phone";
                emailInput.setVisibility(View.GONE);
                passwordInput.setVisibility(View.GONE);
                phoneInput.setVisibility(View.VISIBLE);
            }
        });

        registerButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        progressBar.setVisibility(View.VISIBLE);
        String name = nameInput.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            nameInput.setError("Enter your name");
            progressBar.setVisibility(View.GONE);
            return;
        }

        if (selectedAuthMethod.equals("email")) {
            registerWithEmail();
        } else {
            registerWithPhone();
        }
    }

    private void registerWithEmail() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Enter a valid email");
            progressBar.setVisibility(View.GONE);
            return;
        }

        if (password.length() < 6) {
            passwordInput.setError("Password must be at least 6 characters");
            progressBar.setVisibility(View.GONE);
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void registerWithPhone() {
        String phone = phoneInput.getText().toString().trim();

        if (!Patterns.PHONE.matcher(phone).matches() || phone.length() < 10) {
            phoneInput.setError("Enter a valid phone number");
            progressBar.setVisibility(View.GONE);
            return;
        }

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+91" + phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                        Toast.makeText(RegisterActivity.this, "OTP Auto-Detected!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(RegisterActivity.this, "Failed to send OTP: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId,
                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        Intent intent = new Intent(RegisterActivity.this, OTPVerificationActivity.class);
                        intent.putExtra("phone_number", "+91" + phone);
                        intent.putExtra("verification_id", verificationId);
                        startActivity(intent);
                        finish();
                    }
                })
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}
