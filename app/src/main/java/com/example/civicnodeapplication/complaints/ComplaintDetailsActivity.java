package com.example.civicnodeapplication.complaints;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ComplaintDetailsActivity extends AppCompatActivity {
    private TextView titleTextView, descriptionTextView, statusTextView;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_details);

        titleTextView = findViewById(R.id.complaintTitle);
        descriptionTextView = findViewById(R.id.complaintDescription);
        statusTextView = findViewById(R.id.complaintStatus);

        db = FirebaseFirestore.getInstance();

        String complaintId = getIntent().getStringExtra("complaint_id");
        if (complaintId != null) {
            fetchComplaintDetails(complaintId);
        } else {
            Toast.makeText(this, "Complaint ID not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void fetchComplaintDetails(String complaintId) {
        DocumentReference docRef = db.collection("complaints").document(complaintId);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String title = documentSnapshot.getString("title");
                String description = documentSnapshot.getString("description");
                String status = documentSnapshot.getString("status"); // Example field

                titleTextView.setText(title);
                descriptionTextView.setText(description);
                statusTextView.setText("Status: " + status);
            } else {
                Toast.makeText(this, "Complaint not found", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(e -> {
            Log.e("Firestore", "Error fetching complaint details", e);
            Toast.makeText(this, "Failed to load complaint", Toast.LENGTH_SHORT).show();
        });
    }
}
