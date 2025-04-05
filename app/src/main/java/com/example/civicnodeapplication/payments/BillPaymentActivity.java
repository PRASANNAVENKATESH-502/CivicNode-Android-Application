package com.example.civicnodeapplication.payments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;

public class BillPaymentActivity extends AppCompatActivity {

    private EditText etAmount;
    private Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_payment);

        etAmount = findViewById(R.id.etAmount);
        btnPay = findViewById(R.id.btnPay);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = etAmount.getText().toString().trim();
                if (!amount.isEmpty()) {
                    Toast.makeText(BillPaymentActivity.this, "Payment of ₹" + amount + " successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BillPaymentActivity.this, "Please enter an amount", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
