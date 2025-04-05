package com.example.civicnodeapplication.electricity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;

public class ElectricityBillPaymentActivity extends AppCompatActivity {
    private TextView textBillAmount, textDueDate, textStatus;
    private EditText editUPI;
    private Button btnPayBill;
    private ProgressBar progressBar;

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

        btnPayBill.setOnClickListener(v -> processPayment());

        loadBillDetails();
    }

    private void loadBillDetails() {
        textBillAmount.setText("Bill Amount: ₹1245");
        textDueDate.setText("Due Date: 10-04-2025");
        textStatus.setText("Status: Pending");
    }

    private void processPayment() {
        String upiId = editUPI.getText().toString().trim();

        if (upiId.isEmpty()) {
            Toast.makeText(this, "Please enter UPI ID", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // Simulate payment processing delay
        new android.os.Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(ElectricityBillPaymentActivity.this, "Payment Successful!", Toast.LENGTH_SHORT).show();
            textStatus.setText("Status: Paid");
        }, 3000);
    }
}
