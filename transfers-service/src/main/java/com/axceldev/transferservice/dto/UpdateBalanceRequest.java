package com.axceldev.transferservice.dto;

import com.axceldev.transferservice.model.TransactionType;

public record UpdateBalanceRequest(String accountNumber, double amount, TransactionType transactionType) {
}
