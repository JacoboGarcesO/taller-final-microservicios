package com.axceldev.accountservice.dto;

import com.axceldev.accountservice.model.TransactionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class TransactionsHistoryResponse {
    private String accountNumber;
    private String transactionType;
    private Double amount;
    private String currency;
    private String createdAt;
}
