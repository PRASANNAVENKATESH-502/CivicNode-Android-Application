package com.example.civicnodeapplication.payments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.civicnodeapplication.R;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private List<TransactionModel> transactionList;

    public TransactionAdapter(List<TransactionModel> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionModel transaction = transactionList.get(position);
        holder.txtTransactionId.setText("Transaction ID: " + transaction.getTransactionId());
        holder.txtAmount.setText("Amount: ₹" + transaction.getAmount());
        holder.txtDate.setText(transaction.getDate());
    }

    @Override
    public int getItemCount() { return transactionList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTransactionId, txtAmount, txtDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTransactionId = itemView.findViewById(R.id.txtTransactionId);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
