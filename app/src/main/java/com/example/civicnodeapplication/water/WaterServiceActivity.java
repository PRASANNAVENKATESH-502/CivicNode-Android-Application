package com.example.civicnodeapplication.water;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.databinding.ActivityWaterServiceBinding;

public class WaterServiceActivity extends AppCompatActivity {

    private ActivityWaterServiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWaterServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Fixing the incorrect button reference
        binding.btnRequestService.setOnClickListener(v -> {
            Intent intent = new Intent(WaterServiceActivity.this, WaterConnectionRequestActivity.class);
            startActivity(intent);
        });
    }
}
