package com.axceldev.transferservice.dto;

import com.axceldev.transferservice.model.Currency;
import com.axceldev.transferservice.model.TransactionType;
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
