package com.axceldev.transactionservice.dto;

import com.axceldev.transactionservice.model.Currency;


public record CreateTransactionRequest(String sourceAccountNumber, String destinationAccountNumber,
                                       Double amount, Currency currency) { }
