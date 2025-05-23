package com.axceldev.transactionservice.dto;

import com.axceldev.transactionservice.model.TransactionType;

public record UpdateBalanceRequest(String accountNumber, double amount, TransactionType transactionType) {
}
