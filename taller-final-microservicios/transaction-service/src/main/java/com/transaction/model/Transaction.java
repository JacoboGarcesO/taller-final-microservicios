package com.transaction.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "transactions")
public class Transaction {
  @Id
  private String id;
  private String accountId;
  private BigDecimal amount;
  private LocalDateTime timestamp;
  private String type;
  private String bankId;

  public Transaction() {}

  public Transaction(String accountId, BigDecimal amount, LocalDateTime timestamp, String type, String bankId) {
    this.accountId = accountId;
    this.amount = amount;
    this.timestamp = timestamp;
    this.type = type;
    this.bankId = bankId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getBankId() {
    return bankId;
  }

  public void setBankId(String bankId) {
    this.bankId = bankId;
  }

  // Getters and setters...
}