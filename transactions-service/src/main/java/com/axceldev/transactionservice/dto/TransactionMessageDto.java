package com.axceldev.transactionservice.dto;

import com.axceldev.transactionservice.model.Currency;
import com.axceldev.transactionservice.model.TransactionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class TransactionMessageDto {
    private String accountNumber;
    private Double amount;
    private Currency currency;
    private TransactionType transactionType;
}
