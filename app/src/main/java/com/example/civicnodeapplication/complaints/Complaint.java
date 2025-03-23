package com.example.civicnodeapplication.complaints;

import com.google.firebase.Timestamp;
import java.io.Serializable;

public class Complaint implements Serializable {
    private String id;
    private String userId;
    private String title;
    private String description;
    private String status;
    private Timestamp date;

    public Complaint() { }

    public Complaint(String id, String userId, String title, String description, String status, Timestamp date) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.date = date;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getDate() { return date; }
    public void setDate(Timestamp date) { this.date = date; }
}