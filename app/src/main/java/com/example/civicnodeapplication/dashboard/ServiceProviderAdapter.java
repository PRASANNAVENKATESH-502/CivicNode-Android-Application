package com.example.civicnodeapplication.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.civicnodeapplication.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.civicnodeapplication.complaints.Complaint;

import java.util.List;

public class ServiceProviderAdapter extends RecyclerView.Adapter<ServiceProviderAdapter.ViewHolder> {

    private List<Complaint> complaintList;
    private Context context;
    private FirebaseFirestore db;

    public ServiceProviderAdapter(List<Complaint> complaintList, Context context) {
        this.complaintList = complaintList;
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_provider_complaint, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Complaint complaint = complaintList.get(position);
        holder.textViewTitle.setText(complaint.getTitle());
        holder.textViewDescription.setText(complaint.getDescription());
        holder.textViewStatus.setText("Status: " + complaint.getStatus());

        holder.buttonUpdateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateComplaintStatus(complaint);
            }
        });
    }

    private void updateComplaintStatus(Complaint complaint) {
        String newStatus = getNextStatus(complaint.getStatus());

        db.collection("complaints").document(complaint.getId())
                .update("status", newStatus)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Status updated to " + newStatus, Toast.LENGTH_SHORT).show();
                        complaint.setStatus(newStatus);
                        notifyDataSetChanged();
                    }
                });
    }

    private String getNextStatus(String currentStatus) {
        switch (currentStatus) {
            case "Pending":
                return "In Progress";
            case "In Progress":
                return "Resolved";
            default:
                return "Resolved";
        }
    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewDescription, textViewStatus;
        Button buttonUpdateStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            buttonUpdateStatus = itemView.findViewById(R.id.buttonUpdateStatus);
        }
    }
}
