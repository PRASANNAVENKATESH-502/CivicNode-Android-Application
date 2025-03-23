package com.example.civicnodeapplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import com.google.android.gms.location.*;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationHelper {
    private static final String TAG = "LocationHelper";
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Context context;

    public LocationHelper(Context context) {
        this.context = context;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public interface LocationCallback {
        void onLocationRetrieved(double latitude, double longitude);
        void onError(String errorMessage);
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation(LocationCallback callback) {
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        callback.onLocationRetrieved(location.getLatitude(), location.getLongitude());
                    } else {
                        callback.onError("Failed to get location");
                    }
                })
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    public String getAddressFromLocation(double latitude, double longitude) {
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (!addresses.isEmpty()) {
                return addresses.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            Log.e(TAG, "Failed to get address: " + e.getMessage());
        }
        return "Address not found";
    }
}
