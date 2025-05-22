package com.axceldev.transactionservice.dto;

import com.axceldev.transactionservice.model.Currency;
import com.axceldev.transactionservice.model.TransactionType;


public record CreateTransactionRequest(String accountNumber, TransactionType transactionType,
                                       Double amount, Currency currency, String description) { }
