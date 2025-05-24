package com.example.accountservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("accounts")
public class Account {
    @Id
    private Long id;
    private Long bank_id;
    private String number;
    private BigDecimal balance;

    public Account() {
    }

    public Account(Long id, Long bank_id, String number, BigDecimal balance) {
        this.id = id;
        this.bank_id = bank_id;
        this.number = number;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBank_id() {
        return bank_id;
    }

    public void setBank_id(Long bank_id) {
        this.bank_id = bank_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
