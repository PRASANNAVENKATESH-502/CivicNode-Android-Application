package com.example.civicnodeapplication.complaints;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;

public class ComplaintSubmissionActivity extends AppCompatActivity {
    private EditText editTextComplaintTitle, editTextComplaintDescription;
    private Button buttonSubmitComplaint;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_submission);

        editTextComplaintTitle = findViewById(R.id.editTextComplaintTitle);
        editTextComplaintDescription = findViewById(R.id.editTextComplaintDescription);
        buttonSubmitComplaint = findViewById(R.id.buttonSubmitComplaint);
        progressBar = findViewById(R.id.progressBar);

        buttonSubmitComplaint.setOnClickListener(v -> submitComplaint());
    }

    private void submitComplaint() {
        String title = editTextComplaintTitle.getText().toString().trim();
        String description = editTextComplaintDescription.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // Simulating API call delay
        new android.os.Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(ComplaintSubmissionActivity.this, "Complaint Submitted Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }, 2000);
    }
}
