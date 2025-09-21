package com.phonepe.demo.phonepe_mock.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BankAccountResponse {

    private Long id;
    private String accountNo;
    private String bankName;
    private String ifsc;
    private BigDecimal balance;
    private Boolean active;
    private LocalDateTime createdAt;

    // Constructors
    public BankAccountResponse() {}

    public BankAccountResponse(Long id, String accountNo, String bankName, String ifsc,
                               BigDecimal balance, Boolean active, LocalDateTime createdAt) {
        this.id = id;
        this.accountNo = accountNo;
        this.bankName = bankName;
        this.ifsc = ifsc;
        this.balance = balance;
        this.active = active;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
