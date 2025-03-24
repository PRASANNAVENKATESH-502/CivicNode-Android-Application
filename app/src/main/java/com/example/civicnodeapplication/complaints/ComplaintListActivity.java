package com.example.civicnodeapplication.complaints;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.civicnodeapplication.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class ComplaintListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ComplaintAdapter complaintAdapter;
    private List<Complaint> complaintList;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseFirestore db;
    private CollectionReference complaintsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_list);

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Complaints");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recyclerViewComplaints);
        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        complaintList = new ArrayList<>();
        complaintAdapter = new ComplaintAdapter(complaintList, this, complaint -> {
            Toast.makeText(ComplaintListActivity.this, "Clicked: " + complaint.getTitle(), Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(complaintAdapter);

        db = FirebaseFirestore.getInstance();
        complaintsRef = db.collection("complaints");

        fetchComplaints();

        swipeRefreshLayout.setOnRefreshListener(this::fetchComplaints);
    }

    private void fetchComplaints() {
        progressBar.setVisibility(View.VISIBLE);

        complaintsRef.get().addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);

            if (task.isSuccessful()) {
                QuerySnapshot value = task.getResult();
                if (value != null) {
                    complaintList.clear();
                    for (DocumentSnapshot document : value) {
                        Complaint complaint = document.toObject(Complaint.class);
                        if (complaint != null) {
                            complaint.setId(document.getId());
                            complaintList.add(complaint);
                        }
                    }
                    complaintAdapter.notifyDataSetChanged();
                }
            } else {
                Log.e("ComplaintList", "Firestore Error", task.getException());
                Toast.makeText(ComplaintListActivity.this, "Failed to load complaints", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
