package com.example.civicnodeapplication.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;
import com.example.civicnodeapplication.dashboard.UserDashboardActivity;
import com.example.civicnodeapplication.dashboard.ServiceProviderDashboardActivity;

public class RoleSelectionActivity extends AppCompatActivity {
    private Button userButton, serviceProviderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        userButton = findViewById(R.id.userButton);
        serviceProviderButton = findViewById(R.id.serviceProviderButton);

        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoleSelectionActivity.this, UserDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        serviceProviderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoleSelectionActivity.this, ServiceProviderDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
