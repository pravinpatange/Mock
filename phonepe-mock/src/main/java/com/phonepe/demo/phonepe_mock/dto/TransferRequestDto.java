package com.phonepe.demo.phonepe_mock.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TransferRequestDto {

    @NotNull(message = "Sender account ID is required")
    private Long senderAccountId;

    @NotBlank(message = "Receiver phone number is required")
    private String receiverPhoneNumber;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank(message = "Description is required")
    private String description;

    // Constructors
    public TransferRequestDto() {}

    public TransferRequestDto(Long senderAccountId, String receiverPhoneNumber,
                              BigDecimal amount, String description) {
        this.senderAccountId = senderAccountId;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.amount = amount;
        this.description = description;
    }

    // Getters and Setters
    public Long getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(Long senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public String getReceiverPhoneNumber() {
        return receiverPhoneNumber;
    }

    public void setReceiverPhoneNumber(String receiverPhoneNumber) {
        this.receiverPhoneNumber = receiverPhoneNumber;
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
}
