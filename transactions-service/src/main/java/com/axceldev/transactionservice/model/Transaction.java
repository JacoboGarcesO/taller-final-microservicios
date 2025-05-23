package com.axceldev.transactionservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@Table(name = "transactions")
public class Transaction {
    @Id
    private Long transactionId;
    private String accountNumber;
    private TransactionType transactionType;
    private Double amount;
    private Currency currency;
    @Column("created_at")
    private LocalDateTime createdAt;
}
