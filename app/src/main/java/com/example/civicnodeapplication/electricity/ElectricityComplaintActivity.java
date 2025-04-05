package com.example.civicnodeapplication.electricity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;

public class ElectricityComplaintActivity extends AppCompatActivity {
    private AutoCompleteTextView complaintCategory;
    private EditText complaintDescription;
    private Button submitComplaintButton;
    private ProgressBar progressBar;

    private static final String[] CATEGORIES = {"Power Outage", "Meter Issue", "Billing Problem", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity_complaint);

        complaintCategory = findViewById(R.id.complaintCategory);
        complaintDescription = findViewById(R.id.complaintDescription);
        submitComplaintButton = findViewById(R.id.submitComplaintButton);
        progressBar = findViewById(R.id.progressBar);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, CATEGORIES);
        complaintCategory.setAdapter(adapter);

        submitComplaintButton.setOnClickListener(v -> submitComplaint());
    }

    private void submitComplaint() {
        String category = complaintCategory.getText().toString().trim();
        String description = complaintDescription.getText().toString().trim();

        if (category.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // Simulating complaint submission delay
        new android.os.Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(ElectricityComplaintActivity.this, "Complaint Submitted Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }, 2000);
    }
}
