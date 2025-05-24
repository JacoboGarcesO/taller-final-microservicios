package com.example.accountservice.dto;

public class AccountDTO {
    private Long id;
    private String type;
    private String number;
    private Long bankId;
    private Long customerID;

    public AccountDTO() {
    }

    public AccountDTO(Long id, String type, String number, Long bankId, Long customerID) {
        this.id = id;
        this.type = type;
        this.number = number;
        this.bankId = bankId;
        this.customerID = customerID;
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
}
