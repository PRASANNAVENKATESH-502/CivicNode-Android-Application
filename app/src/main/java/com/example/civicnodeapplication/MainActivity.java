package com.example.civicnodeapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.auth.RoleSelectionActivity;
import com.example.civicnodeapplication.home.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Check if user is logged in
        if (currentUser != null) {
            Log.d("CivicNode", "User is already logged in: " + currentUser.getUid());
            startActivity(new Intent(MainActivity.this, HomeActivity.class));  // ✅ Redirect to HomeActivity
        } else {
            Log.d("CivicNode", "No user logged in, navigating to RoleSelection");
            startActivity(new Intent(MainActivity.this, RoleSelectionActivity.class));
        }

        finish(); // Close MainActivity after redirecting
    }
}
