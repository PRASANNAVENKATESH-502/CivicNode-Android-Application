package com.example.civicnodeapplication.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.HashMap;

public class ServiceProviderProfileActivity extends AppCompatActivity {
    private ImageView profileImage;
    private EditText edtFullName, edtEmail, edtPhone, edtServiceArea, edtSpecialization;
    private Button btnSave;
    private FirebaseAuth auth;
    private DatabaseReference userRef;
    private StorageReference storageRef;
    private Uri imageUri;
    private ProgressDialog progressDialog;

    private static final int IMAGE_PICK_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_profile);

        profileImage = findViewById(R.id.profileImage);
        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtServiceArea = findViewById(R.id.edtServiceArea);
        edtSpecialization = findViewById(R.id.edtSpecialization);
        btnSave = findViewById(R.id.btnSave);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("ServiceProviders").child(user.getUid());
        storageRef = FirebaseStorage.getInstance().getReference("ProfileImages");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating Profile...");

        loadUserData();

        profileImage.setOnClickListener(v -> pickImage());
        btnSave.setOnClickListener(v -> saveProfile());
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            profileImage.setImageURI(imageUri);
        }
    }

    private void loadUserData() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    edtFullName.setText(snapshot.child("fullName").getValue(String.class));
                    edtEmail.setText(snapshot.child("email").getValue(String.class));
                    edtPhone.setText(snapshot.child("phone").getValue(String.class));
                    edtServiceArea.setText(snapshot.child("serviceArea").getValue(String.class));
                    edtSpecialization.setText(snapshot.child("specialization").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ServiceProviderProfileActivity.this, "Error loading data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveProfile() {
        String fullName = edtFullName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String serviceArea = edtServiceArea.getText().toString().trim();
        String specialization = edtSpecialization.getText().toString().trim();

        if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || serviceArea.isEmpty() || specialization.isEmpty()) {
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();
        HashMap<String, Object> profileMap = new HashMap<>();
        profileMap.put("fullName", fullName);
        profileMap.put("email", email);
        profileMap.put("phone", phone);
        profileMap.put("serviceArea", serviceArea);
        profileMap.put("specialization", specialization);

        userRef.updateChildren(profileMap).addOnCompleteListener(task -> {
            progressDialog.dismiss();
            if (task.isSuccessful()) {
                Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Profile update failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
