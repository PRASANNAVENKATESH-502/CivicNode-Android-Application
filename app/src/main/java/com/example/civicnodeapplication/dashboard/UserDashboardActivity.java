package com.example.civicnodeapplication.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.civicnodeapplication.complaints.ComplaintAdapter;

import com.example.civicnodeapplication.R;
import com.example.civicnodeapplication.complaints.ComplaintSubmissionActivity;
import com.example.civicnodeapplication.complaints.Complaint;
import com.example.civicnodeapplication.payments.PaymentActivity;
import com.example.civicnodeapplication.notifications.NotificationsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserDashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerViewComplaints;
    private ComplaintAdapter complaintAdapter;
    private List<Complaint> complaintList;
    private ProgressBar progressBar;

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private TextView textRaiseComplaint, textPayBill, textNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        recyclerViewComplaints = findViewById(R.id.recyclerViewComplaints);
        progressBar = findViewById(R.id.progressBar);
        textRaiseComplaint = findViewById(R.id.textRaiseComplaint);
        textPayBill = findViewById(R.id.textPayBill);
        textNotifications = findViewById(R.id.textNotifications);

        recyclerViewComplaints.setLayoutManager(new LinearLayoutManager(this));
        complaintList = new ArrayList<>();
        complaintAdapter = new ComplaintAdapter(complaintList, this);
        recyclerViewComplaints.setAdapter(complaintAdapter);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        fetchUserComplaints();

        textRaiseComplaint.setOnClickListener(v -> startActivity(new Intent(UserDashboardActivity.this, ComplaintSubmissionActivity.class)));

        textPayBill.setOnClickListener(v -> startActivity(new Intent(UserDashboardActivity.this, BillPaymentActivity.class)));

        textNotifications.setOnClickListener(v -> startActivity(new Intent(UserDashboardActivity.this, NotificationsActivity.class)));
    }

    private void fetchUserComplaints() {
        progressBar.setVisibility(View.VISIBLE);
        String userId = auth.getCurrentUser().getUid();
        CollectionReference complaintsRef = db.collection("complaints");

        complaintsRef.whereEqualTo("userId", userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful() && task.getResult() != null) {
                    complaintList.clear();
                    for (DocumentSnapshot document : task.getResult()) {
                        Complaint complaint = document.toObject(Complaint.class);
                        if (complaint != null) {
                            complaint.setId(document.getId()); // Store document ID
                            complaintList.add(complaint);
                        }
                    }
                    complaintAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(UserDashboardActivity.this, "Failed to load complaints", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
