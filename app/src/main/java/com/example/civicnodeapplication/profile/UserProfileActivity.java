package com.example.civicnodeapplication.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class UserProfileActivity extends AppCompatActivity {

    private ImageView profileImage;
    private EditText edtUserName, edtUserEmail, edtUserPhone;
    private Button btnUpdateProfile;
    private FirebaseAuth auth;
    private DatabaseReference userRef;
    private StorageReference storageRef;
    private ProgressDialog progressDialog;
    private Uri imageUri;

    private static final int IMAGE_PICK_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        profileImage = findViewById(R.id.profileImage);
        edtUserName = findViewById(R.id.edtUserName);
        edtUserEmail = findViewById(R.id.edtUserEmail);
        edtUserPhone = findViewById(R.id.edtUserPhone);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        storageRef = FirebaseStorage.getInstance().getReference("ProfileImages");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating Profile...");

        profileImage.setOnClickListener(v -> pickImage());
        btnUpdateProfile.setOnClickListener(v -> saveProfile());
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    private void saveProfile() {
        String name = edtUserName.getText().toString().trim();
        String email = edtUserEmail.getText().toString().trim();
        String phone = edtUserPhone.getText().toString().trim();

        HashMap<String, Object> profileMap = new HashMap<>();
        profileMap.put("name", name);
        profileMap.put("email", email);
        profileMap.put("phone", phone);

        userRef.updateChildren(profileMap).addOnCompleteListener(task -> {
            progressDialog.dismiss();
            Toast.makeText(this, "Profile Updated!", Toast.LENGTH_SHORT).show();
        });
    }
}
