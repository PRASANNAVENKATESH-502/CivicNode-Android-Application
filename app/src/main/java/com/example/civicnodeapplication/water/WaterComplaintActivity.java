package com.example.civicnodeapplication.water;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;

public class WaterComplaintActivity extends AppCompatActivity {
    private EditText complaintInput;
    private Button submitComplaintButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_complaint);

        complaintInput = findViewById(R.id.complaintInput);
        submitComplaintButton = findViewById(R.id.submitComplaintButton);
        progressBar = findViewById(R.id.progressBar);

        submitComplaintButton.setOnClickListener(v -> submitComplaint());
    }

    private void submitComplaint() {
        String complaintText = complaintInput.getText().toString().trim();

        if (complaintText.isEmpty()) {
            Toast.makeText(this, "Please enter your complaint", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // Simulating a network request delay
        complaintInput.postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Complaint Submitted Successfully!", Toast.LENGTH_SHORT).show();
            complaintInput.setText(""); // Clear input field
        }, 2000);
    }
}
