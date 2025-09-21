package com.phonepe.demo.phonepe_mock.dto;

import com.phonepe.demo.phonepe_mock.entity.TransactionStatus;
import com.phonepe.demo.phonepe_mock.entity.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDto {

    private Long id;
    private String transactionId;
    private BigDecimal amount;
    private String description;
    private TransactionStatus status;
    private TransactionType type;
    private String senderName;
    private String receiverName;
    private LocalDateTime createdAt;

    // Constructors
    public TransactionDto() {}

    public TransactionDto(Long id, String transactionId, BigDecimal amount, String description,
                          TransactionStatus status, TransactionType type, String senderName,
                          String receiverName, LocalDateTime createdAt) {
        this.id = id;
        this.transactionId = transactionId;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.type = type;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
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
