package com.example.civicnodeapplication.complaints;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.civicnodeapplication.R;
import com.google.firebase.firestore.*;

import java.util.ArrayList;
import java.util.List;

public class ComplaintListActivity extends AppCompatActivity implements ComplaintAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private ComplaintAdapter complaintAdapter;
    private List<Complaint> complaintList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_list);

        recyclerView = findViewById(R.id.recyclerViewComplaints);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        complaintList = new ArrayList<>();
        complaintAdapter = new ComplaintAdapter(complaintList, this, this);
        recyclerView.setAdapter(complaintAdapter);

        db = FirebaseFirestore.getInstance();
        fetchComplaints();
    }

    private void fetchComplaints() {
        db.collection("complaints")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(this, "Failed to load complaints", Toast.LENGTH_SHORT).show();
                        Log.e("Firestore", "Error fetching complaints", error);
                        return;
                    }

                    if (value != null && !value.isEmpty()) {
                        complaintList.clear();
                        for (DocumentSnapshot document : value.getDocuments()) {
                            Complaint complaint = document.toObject(Complaint.class);
                            if (complaint != null) {
                                complaint.setId(document.getId());
                                complaintList.add(complaint);
                            }
                        }
                        complaintAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "No complaints found", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onItemClick(Complaint complaint) {
        Intent intent = new Intent(this, ComplaintDetailsActivity.class);
        intent.putExtra("complaint_id", complaint.getId());
        startActivity(intent);
    }
}
