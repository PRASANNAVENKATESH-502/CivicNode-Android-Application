package com.example.civicnodeapplication.water;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;

public class WaterServiceActivity extends AppCompatActivity {
    private Button btnRequestConnection, btnReportIssue, btnPayBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_service);

        btnRequestConnection = findViewById(R.id.btnRequestConnection);
        btnReportIssue = findViewById(R.id.btnReportIssue);
        btnPayBill = findViewById(R.id.btnPayBill);

        btnRequestConnection.setOnClickListener(v ->
                startActivity(new Intent(WaterServiceActivity.this, WaterConnectionRequestActivity.class))
        );

        btnReportIssue.setOnClickListener(v ->
                startActivity(new Intent(WaterServiceActivity.this, WaterComplaintActivity.class))
        );

        btnPayBill.setOnClickListener(v ->
                startActivity(new Intent(WaterServiceActivity.this, WaterBillPaymentActivity.class))
        );
    }
}
