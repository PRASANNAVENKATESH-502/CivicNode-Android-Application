package com.example.civicnodeapplication.electricity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;

public class ElectricityServiceActivity extends AppCompatActivity {

    private ListView listView;
    private String[] services = {
            "Pay Electricity Bill",
            "Raise Electricity Complaint",
            "Request New Connection",
            "Request Service Change"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity_service);

        listView = findViewById(R.id.listView_services);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, services);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(ElectricityServiceActivity.this, ElectricityBillPaymentActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(ElectricityServiceActivity.this, ElectricityComplaintActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(ElectricityServiceActivity.this, NewConnectionRequestActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(ElectricityServiceActivity.this, ServiceChangeRequestActivity.class));
                        break;
                }
            }
        });
    }
}
