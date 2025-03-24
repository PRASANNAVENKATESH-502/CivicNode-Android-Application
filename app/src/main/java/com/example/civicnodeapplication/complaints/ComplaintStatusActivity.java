package com.example.civicnodeapplication.complaints;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.civicnodeapplication.R;
import com.example.civicnodeapplication.complaints.Complaint;

public class ComplaintStatusActivity extends AppCompatActivity {
    private TextView textViewComplaintStatus;
    private Complaint complaint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_status);

        textViewComplaintStatus = findViewById(R.id.textViewComplaintStatus);

        if (getIntent() != null && getIntent().hasExtra("complaint")) {
            complaint = (Complaint) getIntent().getSerializableExtra("complaint");
            textViewComplaintStatus.setText("Status: " + complaint.getStatus());
        }
    }
}
