package com.example.transactionservice.dto;

import com.example.transactionservice.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionStrDTO {
  private Long id;
  private String type;
  private String accountNumber;
  private Long bankId;
  private BigDecimal amount;
  private String description;
  private LocalDateTime timestamp;

  public TransactionStrDTO(Transaction transaction) {
    this.id = transaction.getId();
    this.type = transaction.getType();
    this.accountNumber = String.format("%011d", transaction.getAccountNumber());
    this.bankId = transaction.getBankId();
    this.amount = transaction.getAmount();
    this.description = transaction.getDescription();
    this.timestamp = transaction.getTimestamp();
  }
}
