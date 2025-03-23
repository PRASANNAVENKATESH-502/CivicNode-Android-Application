package com.example.civicnodeapplication.electricity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;
import java.io.IOException;

public class NewConnectionRequestActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText etFullName, etAddress, etMobileNumber, etAadharNumber;
    private ImageView imageViewDocument;
    private Uri documentUri;
    private Button btnUpload, btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_connection_request);

        etFullName = findViewById(R.id.etFullName);
        etAddress = findViewById(R.id.etAddress);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        etAadharNumber = findViewById(R.id.etAadharNumber);
        imageViewDocument = findViewById(R.id.imageViewDocument);
        btnUpload = findViewById(R.id.btnUpload);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRequest();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Document Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            documentUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), documentUri);
                imageViewDocument.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void submitRequest() {
        String fullName = etFullName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String mobileNumber = etMobileNumber.getText().toString().trim();
        String aadharNumber = etAadharNumber.getText().toString().trim();

        if (fullName.isEmpty() || address.isEmpty() || mobileNumber.isEmpty() || aadharNumber.isEmpty() || documentUri == null) {
            Toast.makeText(this, "Please fill all fields and upload a document", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "New Connection Request Submitted Successfully!", Toast.LENGTH_LONG).show();
        finish();
    }
}
