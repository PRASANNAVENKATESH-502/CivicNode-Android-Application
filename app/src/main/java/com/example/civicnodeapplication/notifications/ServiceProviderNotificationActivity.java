package com.example.civicnodeapplication.notifications;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.civicnodeapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ServiceProviderNotificationActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextMessage;
    private Button btnSendNotification;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_notifications);

        editTextTitle = findViewById(R.id.editTextNotificationTitle);
        editTextMessage = findViewById(R.id.editTextNotificationMessage);
        btnSendNotification = findViewById(R.id.btnSendNotification);

        databaseReference = FirebaseDatabase.getInstance().getReference("notifications");

        btnSendNotification.setOnClickListener(view -> sendNotification());
    }

    private void sendNotification() {
        String title = editTextTitle.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(message)) {
            Toast.makeText(this, "Please enter title and message", Toast.LENGTH_SHORT).show();
            return;
        }

        String timestamp = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(new Date());

        NotificationModel notification = new NotificationModel(title, message, timestamp);
        databaseReference.push().setValue(notification)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Notification sent", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to send", Toast.LENGTH_SHORT).show());
    }
}
