package com.example.civicnodeapplication.complaints;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class ComplaintSubmissionActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription;
    private Button buttonSubmit;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_submission);

        editTextTitle = findViewById(R.id.editTextComplaintTitle);
        editTextDescription = findViewById(R.id.editTextComplaintDescription);
        buttonSubmit = findViewById(R.id.buttonSubmitComplaint);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        buttonSubmit.setOnClickListener(v -> submitComplaint());
    }

    private void submitComplaint() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "Unknown";

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> complaintData = new HashMap<>();
        complaintData.put("userId", userId);
        complaintData.put("title", title);
        complaintData.put("description", description);
        complaintData.put("status", "Pending");
        complaintData.put("date", Timestamp.now());

        db.collection("complaints").add(complaintData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Complaint Submitted Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error submitting complaint", Toast.LENGTH_SHORT).show());
    }
}
