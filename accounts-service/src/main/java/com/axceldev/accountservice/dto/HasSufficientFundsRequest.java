package com.axceldev.accountservice.dto;

public record HasSufficientFundsRequest(String accountNumber, Double amount) {
}
