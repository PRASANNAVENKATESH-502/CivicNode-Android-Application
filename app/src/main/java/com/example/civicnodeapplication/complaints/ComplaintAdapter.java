package com.example.civicnodeapplication.complaints;

import com.example.civicnodeapplication.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_complaint, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Complaint complaint = complaintList.get(position);
        holder.txtComplaintTitle.setText(complaint.getTitle());
        holder.txtComplaintDescription.setText(complaint.getDescription());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(complaint));
    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtComplaintTitle, txtComplaintDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            txtComplaintTitle = itemView.findViewById(R.id.txtComplaintTitle);
            txtComplaintDescription = itemView.findViewById(R.id.txtComplaintDescription);
        }
    }
}
