package com.example.civicnodeapplication.payments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.civicnodeapplication.R;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {
    private List<PaymentModel> paymentList;

    public PaymentAdapter(List<PaymentModel> paymentList) { this.paymentList = paymentList; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PaymentModel payment = paymentList.get(position);
        holder.txtTransactionId.setText("Transaction ID: " + payment.getTransactionId());
        holder.txtAmount.setText("Amount: ₹" + payment.getAmount());
        holder.txtDate.setText(payment.getDate());
    }

    @Override
    public int getItemCount() { return paymentList.size(); }

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
