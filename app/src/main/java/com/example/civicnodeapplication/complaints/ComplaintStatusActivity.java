package com.example.civicnodeapplication.complaints;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;
import com.google.firebase.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ComplaintStatusActivity extends AppCompatActivity {
    private TextView textViewComplaintTitle, textViewComplaintDescription, textViewComplaintStatus, textViewComplaintDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaints);

        textViewComplaintTitle = findViewById(R.id.textViewComplaintTitle);
        textViewComplaintDescription = findViewById(R.id.textViewComplaintDescription);
        textViewComplaintStatus = findViewById(R.id.textViewComplaintStatus);
        textViewComplaintDate = findViewById(R.id.textViewComplaintDate);

        Complaint complaint = (Complaint) getIntent().getSerializableExtra("complaint");
        if (complaint != null) {
            textViewComplaintTitle.setText(complaint.getTitle());
            textViewComplaintDescription.setText(complaint.getDescription());
            textViewComplaintStatus.setText("Status: " + complaint.getStatus());
            textViewComplaintDate.setText("Submitted on: " + formatDate(complaint.getDate()));
        }
    }

    private String formatDate(Timestamp timestamp) {
        if (timestamp != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
            return sdf.format(timestamp.toDate());
        }
        return "Unknown";
    }
}
