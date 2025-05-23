package com.axceldev.accountservice.dto;

import com.axceldev.accountservice.model.TransactionType;

public record UpdateBalanceRequest(String accountNumber, double amount, TransactionType transactionType) {
}
