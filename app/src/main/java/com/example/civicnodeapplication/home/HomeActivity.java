package com.example.civicnodeapplication.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;
import com.example.civicnodeapplication.electricity.ElectricityServiceActivity;
import com.example.civicnodeapplication.water.WaterServiceActivity;
import com.example.civicnodeapplication.dashboard.UserDashboardActivity;
import com.example.civicnodeapplication.profile.UserProfileActivity;

public class HomeActivity extends AppCompatActivity {

    private Button btnElectricity, btnWater, btnDashboard, btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnElectricity = findViewById(R.id.btnElectricity);
        btnWater = findViewById(R.id.btnWater);
        btnDashboard = findViewById(R.id.btnDashboard);
        btnProfile = findViewById(R.id.btnProfile);

        // Navigate to Electricity Services
        btnElectricity.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ElectricityServiceActivity.class);
            startActivity(intent);
        });

        // Navigate to Water Services
        btnWater.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, WaterServiceActivity.class);
            startActivity(intent);
        });

        // Navigate to User Dashboard
        btnDashboard.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, UserDashboardActivity.class);
            startActivity(intent);
        });

        // Navigate to User Profile
        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, UserProfileActivity.class);
            startActivity(intent);
        });
    }
}
