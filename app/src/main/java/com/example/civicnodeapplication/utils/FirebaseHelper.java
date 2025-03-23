package com.example.civicnodeapplication.utils;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.HashMap;

public class FirebaseHelper {
    private static final String TAG = "FirebaseHelper";
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    public FirebaseHelper() {
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
    }

    public interface AuthCallback {
        void onSuccess(FirebaseUser user);
        void onFailure(String errorMessage);
    }

    public interface DataCallback {
        void onDataReceived(HashMap<String, String> data);
        void onError(String error);
    }

    public void registerUser(String email, String password, AuthCallback callback) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        callback.onSuccess(user);
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    public void loginUser(String email, String password, AuthCallback callback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        callback.onSuccess(user);
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    public void signOutUser() {
        auth.signOut();
    }

    public void saveUserData(String userId, HashMap<String, String> data) {
        databaseReference.child(userId).setValue(data)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "User data saved"))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to save data: " + e.getMessage()));
    }

    public void getUserData(String userId, DataCallback callback) {
        databaseReference.child(userId).get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        HashMap<String, String> userData = (HashMap<String, String>) snapshot.getValue();
                        callback.onDataReceived(userData);
                    } else {
                        callback.onError("No data found");
                    }
                })
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }
}
