package com.example.civicnodeapplication.electricity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;
import java.io.IOException;

public class ServiceChangeRequestActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText etFullName, etAccountNumber, etMobileNumber;
    private Spinner spinnerServiceType;
    private ImageView imageViewDocument;
    private Uri documentUri;
    private Button btnUpload, btnSubmit;
    private String selectedServiceType = "Upgrade Connection";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_change_request);

        etFullName = findViewById(R.id.etFullName);
        etAccountNumber = findViewById(R.id.etAccountNumber);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        spinnerServiceType = findViewById(R.id.spinnerServiceType);
        imageViewDocument = findViewById(R.id.imageViewDocument);
        btnUpload = findViewById(R.id.btnUpload);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Set up spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.service_change_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServiceType.setAdapter(adapter);
        spinnerServiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedServiceType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnUpload.setOnClickListener(v -> openFileChooser());
        btnSubmit.setOnClickListener(v -> submitRequest());
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Supporting Document"), PICK_IMAGE_REQUEST);
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
        String accountNumber = etAccountNumber.getText().toString().trim();
        String mobileNumber = etMobileNumber.getText().toString().trim();

        if (fullName.isEmpty() || accountNumber.isEmpty() || mobileNumber.isEmpty() || documentUri == null) {
            Toast.makeText(this, "Please fill all fields and upload a document", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Service Change Request Submitted Successfully!", Toast.LENGTH_LONG).show();
        finish();
    }
}
