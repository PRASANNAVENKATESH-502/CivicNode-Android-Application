package com.example.civicnodeapplication.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.civicnodeapplication.R;
import com.example.civicnodeapplication.complaints.Complaint;
import com.google.firebase.firestore.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProviderDashboardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ServiceProviderAdapter adapter;
    private List<Complaint> complaintList;
    private FirebaseFirestore db;
    private ListenerRegistration complaintListener; // Firestore real-time listener

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_dashboard);

        recyclerView = findViewById(R.id.recyclerViewComplaints);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        complaintList = new ArrayList<>();
        adapter = new ServiceProviderAdapter(complaintList, this);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        fetchComplaints();
    }

    private void fetchComplaints() {
        complaintListener = db.collection("complaints")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(this, "Failed to fetch complaints", Toast.LENGTH_SHORT).show();
                        Log.e("Firestore", "Error fetching complaints", error);
                        return;
                    }

                    if (value != null) {
                        complaintList.clear();
                        for (DocumentSnapshot document : value.getDocuments()) {
                            Complaint complaint = document.toObject(Complaint.class);
                            if (complaint != null) {
                                complaint.setId(document.getId());
                                complaintList.add(complaint);
                            }
                        }
                        adapter.notifyDataSetChanged(); // Efficient update
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (complaintListener != null) {
            complaintListener.remove(); // Stop listening when activity is destroyed
        }
    }
}
