package com.example.civicnodeapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.auth.LoginActivity;
import com.example.civicnodeapplication.water.WaterServiceActivity;
import com.example.civicnodeapplication.complaints.ComplaintSubmissionActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin, btnReportIssue, btnWaterServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        btnReportIssue = findViewById(R.id.btnReportIssue);
        btnWaterServices = findViewById(R.id.btnWaterServices);

        btnLogin.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));
        btnReportIssue.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ReportIssueActivity.class)));
        btnWaterServices.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, WaterServiceActivity.class)));
    }
}
