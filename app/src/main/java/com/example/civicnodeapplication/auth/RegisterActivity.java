package com.example.civicnodeapplication.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.civicnodeapplication.R;
import com.example.civicnodeapplication.dashboard.ServiceProviderDashboardActivity;
import com.example.civicnodeapplication.dashboard.UserDashboardActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailField, passwordField;
    private RadioGroup roleGroup;
    private RadioButton radioUser, radioServiceProvider;
    private Button registerBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Ensure this matches your XML filename

        // Initialize UI elements with correct IDs
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        roleGroup = findViewById(R.id.roleGroup);
        radioUser = findViewById(R.id.radio_user);
        radioServiceProvider = findViewById(R.id.radio_service_provider);
        registerBtn = findViewById(R.id.registerBtn);

        // Register button click event
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        int selectedRoleId = roleGroup.getCheckedRadioButtonId();

        // Validate input fields
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedRoleId == -1) {
            Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
            return;
        }

        // Determine role based on selected RadioButton
        String role = (selectedRoleId == R.id.radio_user) ? "user" : "service_provider";

        // Simulate successful registration and navigate to the respective dashboard
        Toast.makeText(this, "Registration successful as " + role, Toast.LENGTH_SHORT).show();

        Intent intent = (role.equals("user"))
                ? new Intent(RegisterActivity.this, UserDashboardActivity.class)
                : new Intent(RegisterActivity.this, ServiceProviderDashboardActivity.class);

        startActivity(intent);
        finish(); // Close RegisterActivity after navigation
    }
}
