package com.example.civicnodeapplication.payments;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.civicnodeapplication.R;
import com.google.firebase.database.*;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.*;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    private EditText etAmount;
    private Button btnPay;
    private RecyclerView recyclerView;
    private List<PaymentModel> paymentList;
    private PaymentAdapter adapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        etAmount = findViewById(R.id.etAmount);
        btnPay = findViewById(R.id.btnPay);
        recyclerView = findViewById(R.id.recyclerViewPayments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        paymentList = new ArrayList<>();
        adapter = new PaymentAdapter(paymentList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("payments");

        btnPay.setOnClickListener(view -> startPayment());

        loadPaymentHistory();
    }

    private void startPayment() {
        String amountText = etAmount.getText().toString().trim();
        if (amountText.isEmpty()) {
            Toast.makeText(this, "Enter amount", Toast.LENGTH_SHORT).show();
            return;
        }

        int amount = Math.round(Float.parseFloat(amountText) * 100);

        Checkout checkout = new Checkout();
        checkout.setKeyID("YOUR_RAZORPAY_KEY_ID"); // Replace with your Razorpay Key ID

        try {
            JSONObject options = new JSONObject();
            options.put("name", "CivicNode Payments");
            options.put("description", "Electricity Bill Payment");
            options.put("currency", "INR");
            options.put("amount", amount);

            checkout.open(this, options);
        } catch (Exception e) {
            Toast.makeText(this, "Error in Payment: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();

        String amount = etAmount.getText().toString().trim();
        String timestamp = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(new Date());

        PaymentModel payment = new PaymentModel(razorpayPaymentID, amount, timestamp);
        databaseReference.push().setValue(payment);

        etAmount.setText("");
        loadPaymentHistory();
    }

    @Override
    public void onPaymentError(int code, String response) {
        Toast.makeText(this, "Payment Failed: " + response, Toast.LENGTH_SHORT).show();
    }

    private void loadPaymentHistory() {
        databaseReference.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                paymentList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    PaymentModel payment = data.getValue(PaymentModel.class);
                    paymentList.add(0, payment);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }
}
