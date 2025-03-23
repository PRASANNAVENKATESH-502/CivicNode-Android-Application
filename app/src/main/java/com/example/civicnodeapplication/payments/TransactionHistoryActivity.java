package com.example.civicnodeapplication.payments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.civicnodeapplication.R;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private List<TransactionModel> transactionList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        recyclerView = findViewById(R.id.recyclerViewTransactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        transactionList = new ArrayList<>();
        loadTransactionData();  // Load mock transactions (replace with real data from Firebase)

        adapter = new TransactionAdapter(transactionList);
        recyclerView.setAdapter(adapter);
    }

    private void loadTransactionData() {
        transactionList.add(new TransactionModel("TXN12345", "500", "21 March 2025"));
        transactionList.add(new TransactionModel("TXN67890", "1500", "20 March 2025"));
        transactionList.add(new TransactionModel("TXN11121", "250", "19 March 2025"));
        transactionList.add(new TransactionModel("TXN54321", "750", "18 March 2025"));
    }
}
