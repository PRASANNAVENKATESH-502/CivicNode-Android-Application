package com.example.civicnodeapplication.water;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;

public class WaterBillPaymentActivity extends AppCompatActivity {
    private EditText accountNumberInput, amountInput;
    private Button payBillButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_bill_payment);

        accountNumberInput = findViewById(R.id.accountNumberInput);
        amountInput = findViewById(R.id.amountInput);
        payBillButton = findViewById(R.id.payBillButton);
        progressBar = findViewById(R.id.progressBar);

        payBillButton.setOnClickListener(v -> processPayment());
    }

    private void processPayment() {
        String accountNumber = accountNumberInput.getText().toString().trim();
        String amount = amountInput.getText().toString().trim();

        if (accountNumber.isEmpty() || amount.isEmpty()) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        payBillButton.setEnabled(false);

        // Simulate payment processing
        payBillButton.postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            payBillButton.setEnabled(true);
            Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show();
            accountNumberInput.setText("");
            amountInput.setText("");
        }, 2500);
    }
}
