package com.example.accountservice.dto;

import com.example.accountservice.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountStrDTO {
  private String accountNumber;
  private String accountType;
  private BigDecimal balance;
  private String status;
  private Long bankId;

  public AccountStrDTO(Account account) {
    this.accountNumber = String.format("%011d", account.getAccountNumber());
    this.accountType = account.getAccountType();
    this.balance = account.getBalance();
    this.status = account.getStatus();
    this.bankId = account.getBankId();
  }
}
