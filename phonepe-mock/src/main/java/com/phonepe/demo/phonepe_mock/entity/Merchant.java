package com.phonepe.demo.phonepe_mock.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "merchants")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String upiId;

    @Column(nullable = false, unique = true)
    private String accountNo;

    @Column
    private String contact;

    // Constructors
    public Merchant() {}

    public Merchant(String name, String upiId, String accountNo, String contact) {
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
