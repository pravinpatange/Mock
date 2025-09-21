package com.phonepe.demo.phonepe_mock.dto;

import java.math.BigDecimal;

public class BankAccountDto {

    private Long id;
    private String accountNumber;
    private String bankName;
    private String ifscCode;
    private BigDecimal balance;
    private Boolean active;

    // Constructors
    public BankAccountDto() {}

    public BankAccountDto(Long id, String accountNumber, String bankName,
                          String ifscCode, BigDecimal balance, Boolean active) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.ifscCode = ifscCode;
        this.balance = balance;
        this.active = active;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
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
}
