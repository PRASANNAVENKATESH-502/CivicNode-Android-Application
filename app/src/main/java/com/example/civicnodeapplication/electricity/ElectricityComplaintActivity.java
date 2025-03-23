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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ElectricityComplaintActivity extends AppCompatActivity {

    private AutoCompleteTextView complaintCategory;
    private EditText complaintDescription;
    private Button submitComplaintButton;
    private ProgressBar progressBar;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private static final String[] categories = {
            "Power Outage", "Voltage Fluctuation", "Meter Issue", "Billing Discrepancy", "Other"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity_complaint);

        complaintCategory = findViewById(R.id.complaintCategory);
        complaintDescription = findViewById(R.id.complaintDescription);
        submitComplaintButton = findViewById(R.id.submitComplaintButton);
        progressBar = findViewById(R.id.progressBar);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Set up dropdown menu for complaint categories
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categories);
        complaintCategory.setAdapter(adapter);

        submitComplaintButton.setOnClickListener(v -> submitComplaint());
    }

    private void submitComplaint() {
        String category = complaintCategory.getText().toString().trim();
        String description = complaintDescription.getText().toString().trim();

        if (category.isEmpty()) {
            Toast.makeText(this, "Please select a complaint category", Toast.LENGTH_SHORT).show();
            return;
        }
        if (description.isEmpty()) {
            Toast.makeText(this, "Please provide a description", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        submitComplaintButton.setEnabled(false);

        String userId = auth.getCurrentUser().getUid();
        String complaintId = "CMP-" + System.currentTimeMillis();

        Map<String, Object> complaint = new HashMap<>();
        complaint.put("userId", userId);
        complaint.put("category", category);
        complaint.put("description", description);
        complaint.put("status", "Pending");
        complaint.put("submittedAt", new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date()));

        db.collection("electricity_complaints").document(complaintId)
                .set(complaint)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    submitComplaintButton.setEnabled(true);

                    if (task.isSuccessful()) {
                        Toast.makeText(ElectricityComplaintActivity.this, "Complaint Submitted Successfully", Toast.LENGTH_LONG).show();
                        complaintCategory.setText("");
                        complaintDescription.setText("");
                    } else {
                        Toast.makeText(ElectricityComplaintActivity.this, "Failed to submit complaint", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
