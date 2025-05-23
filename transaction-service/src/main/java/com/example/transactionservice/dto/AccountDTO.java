package com.example.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
  private Long accountNumber;
  private String accountType;
  private BigDecimal balance;
  private String status;
  private Long bankId;
}
