package com.phonepe.demo.phonepe_mock.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class BankAccountRequest {

    @NotBlank(message = "Account number is required")
    private String accountNo;

    @NotBlank(message = "Bank name is required")
    private String bankName;

    @NotBlank(message = "IFSC is required")
    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code")
    private String ifsc;

    @NotNull(message = "User ID is required")
    private Long userId;

    @DecimalMin(value = "0.0", message = "Initial balance cannot be negative")
    private BigDecimal initialBalance = BigDecimal.ZERO;

    // Constructors
    public BankAccountRequest() {}

    public BankAccountRequest(String accountNo, String bankName, String ifsc,
                              Long userId, BigDecimal initialBalance) {
        this.accountNo = accountNo;
        this.bankName = bankName;
        this.ifsc = ifsc;
        this.userId = userId;
        this.initialBalance = initialBalance;
    }

    // Getters and Setters
    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }
}
