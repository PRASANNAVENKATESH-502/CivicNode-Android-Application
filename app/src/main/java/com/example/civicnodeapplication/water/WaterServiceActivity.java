package com.example.civicnodeapplication.water;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;
import com.example.civicnodeapplication.complaints.ComplaintSubmissionActivity;
import com.example.civicnodeapplication.payments.PaymentActivity;
import com.example.civicnodeapplication.notifications.NotificationsActivity;

public class WaterServiceActivity extends AppCompatActivity {
    private Button raiseComplaint, payBill, serviceNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_service);

        raiseComplaint = findViewById(R.id.raiseComplaint);
        payBill = findViewById(R.id.payBill);
        serviceNotifications = findViewById(R.id.serviceNotifications);

        raiseComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaterServiceActivity.this, ComplaintSubmissionActivity.class);
                startActivity(intent);
            }
        });

        payBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaterServiceActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });

        serviceNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaterServiceActivity.this, NotificationsActivity.class);
                startActivity(intent);
            }
        });
    }
}
