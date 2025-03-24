package com.example.civicnodeapplication.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;

public class RoleSelectionActivity extends AppCompatActivity {
    private Button userButton, adminButton, technicianButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        // Corrected IDs to match XML file
        userButton = findViewById(R.id.btn_user);
        adminButton = findViewById(R.id.btn_admin);
        technicianButton = findViewById(R.id.btn_technician);

        userButton.setOnClickListener(v -> navigateToRegister("user"));
        adminButton.setOnClickListener(v -> navigateToRegister("admin"));
        technicianButton.setOnClickListener(v -> navigateToRegister("technician"));
    }

    private void navigateToRegister(String role) {
        Intent intent = new Intent(RoleSelectionActivity.this, RegisterActivity.class);
        intent.putExtra("role", role);
        startActivity(intent);
    }
}
