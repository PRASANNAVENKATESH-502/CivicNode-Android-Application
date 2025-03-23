package com.example.civicnodeapplication.electricity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.civicnodeapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ElectricityBillPaymentActivity extends AppCompatActivity {

    private TextView textBillAmount, textDueDate, textStatus;
    private EditText editUPI;
    private Button btnPayBill;
    private ProgressBar progressBar;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private String billId, billAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity_bill_payment);

        textBillAmount = findViewById(R.id.textBillAmount);
        textDueDate = findViewById(R.id.textDueDate);
        textStatus = findViewById(R.id.textStatus);
        editUPI = findViewById(R.id.editUPI);
        btnPayBill = findViewById(R.id.btnPayBill);
        progressBar = findViewById(R.id.progressBar);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        fetchBillDetails();

        btnPayBill.setOnClickListener(v -> initiateUPIPayment());
    }

    private void fetchBillDetails() {
        progressBar.setVisibility(View.VISIBLE);
        String userId = auth.getCurrentUser().getUid();
        DocumentReference billRef = db.collection("electricity_bills").document(userId);

        billRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful() && task.getResult().exists()) {
                    DocumentSnapshot document = task.getResult();
                    billId = document.getId();
                    billAmount = document.getString("amount");
                    String dueDate = document.getString("due_date");
                    String status = document.getString("status");

                    textBillAmount.setText("₹ " + billAmount);
                    textDueDate.setText("Due Date: " + dueDate);
                    textStatus.setText("Status: " + status);

                    if ("Paid".equals(status)) {
                        btnPayBill.setEnabled(false);
                        btnPayBill.setText("Paid");
                    }
                } else {
                    Toast.makeText(ElectricityBillPaymentActivity.this, "No bill found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initiateUPIPayment() {
        String upiId = editUPI.getText().toString().trim();
        if (upiId.isEmpty()) {
            Toast.makeText(this, "Enter UPI ID", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uri = new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", "CivicNode Electricity")
                .appendQueryParameter("mc", "")
                .appendQueryParameter("tid", "T" + System.currentTimeMillis())
                .appendQueryParameter("tr", billId)
                .appendQueryParameter("tn", "Electricity Bill Payment")
                .appendQueryParameter("am", billAmount)
                .appendQueryParameter("cu", "INR")
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setPackage("com.google.android.apps.nbu.paisa.user"); // Google Pay
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            updatePaymentStatus();
        }
    }

    private void updatePaymentStatus() {
        progressBar.setVisibility(View.VISIBLE);
        DocumentReference billRef = db.collection("electricity_bills").document(billId);

        billRef.update("status", "Paid", "paid_on", new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()))
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(ElectricityBillPaymentActivity.this, "Payment Successful", Toast.LENGTH_SHORT).show();
                        textStatus.setText("Status: Paid");
                        btnPayBill.setEnabled(false);
                        btnPayBill.setText("Paid");
                    } else {
                        Toast.makeText(ElectricityBillPaymentActivity.this, "Failed to update status", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
