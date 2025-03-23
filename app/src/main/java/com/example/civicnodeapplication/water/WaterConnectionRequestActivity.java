package com.example.civicnodeapplication.water;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;

public class WaterConnectionRequestActivity extends AppCompatActivity {
    private EditText nameInput, addressInput, mobileInput;
    private Button submitRequestButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_connection_request);

        nameInput = findViewById(R.id.nameInput);
        addressInput = findViewById(R.id.addressInput);
        mobileInput = findViewById(R.id.mobileInput);
        submitRequestButton = findViewById(R.id.submitRequestButton);
        progressBar = findViewById(R.id.progressBar);

        submitRequestButton.setOnClickListener(v -> submitConnectionRequest());
    }

    private void submitConnectionRequest() {
        String name = nameInput.getText().toString().trim();
        String address = addressInput.getText().toString().trim();
        String mobile = mobileInput.getText().toString().trim();

        if (name.isEmpty() || address.isEmpty() || mobile.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // Simulate a network request delay
        submitRequestButton.postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Connection Request Submitted!", Toast.LENGTH_SHORT).show();
            nameInput.setText("");
            addressInput.setText("");
            mobileInput.setText("");
        }, 2000);
    }
}
