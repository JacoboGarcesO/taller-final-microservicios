package com.axceldev.accountservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Builder(toBuilder = true)
@Table(name = "accounts")
public class Account {
    @Id
    private Long accountId;
    private String accountNumber;
    private AccountType accountType;
    private Currency currency;
    private Double balance;
    private Long bankId;
}
