package com.example.civicnodeapplication.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.civicnodeapplication.R;
import com.example.civicnodeapplication.complaints.Complaint;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ServiceProviderDashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ServiceProviderAdapter serviceProviderAdapter;
    private List<Complaint> complaintList;
    private ProgressBar progressBar;

    private FirebaseFirestore db;
    private CollectionReference complaintsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_dashboard);

        recyclerView = findViewById(R.id.recyclerViewComplaints);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        complaintList = new ArrayList<>();
        serviceProviderAdapter = new ServiceProviderAdapter(complaintList, this);

        recyclerView.setAdapter(serviceProviderAdapter);

        db = FirebaseFirestore.getInstance();
        complaintsRef = db.collection("complaints");

        fetchComplaints();
    }

    private void fetchComplaints() {
        progressBar.setVisibility(View.VISIBLE);

        complaintsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                    serviceProviderAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ServiceProviderDashboardActivity.this, "Failed to load complaints", Toast.LENGTH_SHORT).show();
                    Log.e("ServiceProviderDashboard", "Error: ", task.getException());
                }
            }
        });
    }
}
