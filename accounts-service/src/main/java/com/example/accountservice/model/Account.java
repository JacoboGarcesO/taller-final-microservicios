package com.example.accountservice.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("accounts")
public class Account {

    @Id
    @Column("account_id")
    private Long id;
    private String type;
    private String number;
    @Column("bank_id")
    private Long bankId;
    @Column("customer_id")
    private Long customerID;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    public Account() {
    }

    public Account(Long id, String type, String number, Long bankId, Long customerID, LocalDateTime createdAt) {
        this.id = id;
        this.type = type;
        this.number = number;
        this.bankId = bankId;
        this.customerID = customerID;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
