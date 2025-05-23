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
public class TransferDTO {
  private Long sourceAccountId;
  private Long destinationAccountId;
  private BigDecimal amount;
}
