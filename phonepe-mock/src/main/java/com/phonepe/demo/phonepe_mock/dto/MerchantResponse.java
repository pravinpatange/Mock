package com.phonepe.demo.phonepe_mock.dto;

public class MerchantResponse {

    private Long id;
    private String name;
    private String upiId;
    private String accountNo;
    private String contact;

    // Constructors
    public MerchantResponse() {}

    public MerchantResponse(Long id, String name, String upiId, String accountNo, String contact) {
        this.id = id;
        this.name = name;
        this.upiId = upiId;
        this.accountNo = accountNo;
        this.contact = contact;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
