package com.phonepe.demo.phonepe_mock.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class DepositRequestDto {

    @NotNull(message = "Account ID is required")
    private Long accountId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank(message = "Description is required")
    private String description;

    // Default constructor
    public DepositRequestDto() {}

    // Constructor with parameters
    public DepositRequestDto(Long accountId, BigDecimal amount, String description) {
        this.accountId = accountId;
        this.amount = amount;
        this.description = description;
    }

    // Getters
    public Long getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // toString method (optional, for debugging)
    @Override
    public String toString() {
        return "DepositRequestDto{" +
                "accountId=" + accountId +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }

    // equals and hashCode (optional, for proper object comparison)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepositRequestDto that = (DepositRequestDto) o;

        if (!accountId.equals(that.accountId)) return false;
        if (!amount.equals(that.amount)) return false;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        int result = accountId.hashCode();
        result = 31 * result + amount.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
