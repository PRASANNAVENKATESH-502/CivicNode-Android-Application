package com.example.civicnodeapplication.utils;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.civicnodeapplication.complaints.Complaint;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class FirebaseHelper {

    public static void fetchComplaints(DatabaseReference reference, final DataCallback<List<Complaint>> callback) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Complaint> complaints = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    try {
                        Complaint complaint = data.getValue(Complaint.class);
                        if (complaint != null) {
                            complaints.add(complaint);
                        }
                    } catch (Exception e) {
                        Log.e("FirebaseHelper", "Data parsing error: " + e.getMessage());
                    }
                }
                callback.onSuccess(complaints);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseHelper", "Database error: " + error.getMessage());
            }
        });
    }

    public interface DataCallback<T> {
        void onSuccess(T result);
    }
}
