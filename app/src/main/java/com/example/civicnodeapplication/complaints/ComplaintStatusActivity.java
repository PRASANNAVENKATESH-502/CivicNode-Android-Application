package com.example.civicnodeapplication.complaints;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;

public class ComplaintStatusActivity extends AppCompatActivity {
    private TextView textViewComplaintTitle, textViewComplaintStatus, textViewComplaintDescription, textViewComplaintDate;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_status);

        textViewComplaintTitle = findViewById(R.id.textViewComplaintTitle);
        textViewComplaintStatus = findViewById(R.id.textViewComplaintStatus);
        textViewComplaintDescription = findViewById(R.id.textViewComplaintDescription);
        textViewComplaintDate = findViewById(R.id.textViewComplaintDate);
        progressBar = findViewById(R.id.progressBar);

        // Fetch complaint details (Mock data for now)
        loadComplaintDetails();
    }

    private void loadComplaintDetails() {
        progressBar.setVisibility(View.VISIBLE);

        // Simulate fetching data
        textViewComplaintTitle.setText("Power Outage Issue");
        textViewComplaintStatus.setText("Status: In Progress");
        textViewComplaintDescription.setText("Electricity has been out since last night in our area.");
        textViewComplaintDate.setText("Submitted on: 30-03-2025");

        progressBar.setVisibility(View.GONE);
    }
}
