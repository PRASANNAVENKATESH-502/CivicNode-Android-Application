package com.example.civicnodeapplication.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;
import com.example.civicnodeapplication.electricity.ElectricityServiceActivity;
import com.example.civicnodeapplication.water.WaterServiceActivity;

public class UserDashboardActivity extends AppCompatActivity {
    private Button electricityButton, waterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        electricityButton = findViewById(R.id.electricityButton);
        waterButton = findViewById(R.id.waterButton);

        electricityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboardActivity.this, ElectricityServiceActivity.class);
                startActivity(intent);
            }
        });

        waterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboardActivity.this, WaterServiceActivity.class);
                startActivity(intent);
            }
        });
    }
}
