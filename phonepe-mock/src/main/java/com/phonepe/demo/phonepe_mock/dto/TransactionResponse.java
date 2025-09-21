package com.phonepe.demo.phonepe_mock.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {

    private Long id;
    private String transactionId;
    private String type;
    private String status;
    private BigDecimal amount;
    private String remarks;
    private String senderName;
    private String receiverName;
    private LocalDateTime createdAt;

    // Constructors
    public TransactionResponse() {}

    public TransactionResponse(Long id, String transactionId, String type, String status,
                               BigDecimal amount, String remarks, String senderName,
                               String receiverName, LocalDateTime createdAt) {
        this.id = id;
        this.transactionId = transactionId;
        this.type = type;
        this.status = status;
        this.amount = amount;
        this.remarks = remarks;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
