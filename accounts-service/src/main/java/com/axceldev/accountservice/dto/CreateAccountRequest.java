package com.axceldev.accountservice.dto;

import com.axceldev.accountservice.model.AccountType;
import com.axceldev.accountservice.model.Currency;

public record CreateAccountRequest(String accountNumber, AccountType accountType,
                                   Currency currency, Long bankId) { }
