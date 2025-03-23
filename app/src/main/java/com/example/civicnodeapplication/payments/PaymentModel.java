package com.example.civicnodeapplication.payments;

public class PaymentModel {
    private String transactionId;
    private String amount;
    private String date;

    public PaymentModel() { }

    public PaymentModel(String transactionId, String amount, String date) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.date = date;
    }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
