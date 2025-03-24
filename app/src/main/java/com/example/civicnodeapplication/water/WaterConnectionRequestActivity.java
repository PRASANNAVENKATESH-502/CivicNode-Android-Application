package com.example.civicnodeapplication.water;

import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;

public class WaterConnectionRequestActivity extends AppCompatActivity {

    private EditText etFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_connection_request);

        // Fixing incorrect ID reference
        etFullName = findViewById(R.id.etFullName);
    }
}
