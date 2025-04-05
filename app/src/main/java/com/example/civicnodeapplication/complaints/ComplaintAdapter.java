package com.example.civicnodeapplication.complaints;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.civicnodeapplication.R;
import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ViewHolder> {

    private List<Complaint> complaintList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Complaint complaint);
    }

    public ComplaintAdapter(List<Complaint> complaintList, Context context, OnItemClickListener listener) {
        this.complaintList = complaintList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complaint, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Complaint complaint = complaintList.get(position);
        holder.titleTextView.setText(complaint.getTitle());
        holder.descriptionTextView.setText(complaint.getDescription());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(complaint));
    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.complaintTitle);
            descriptionTextView = itemView.findViewById(R.id.complaintDescription);
        }
    }
}
