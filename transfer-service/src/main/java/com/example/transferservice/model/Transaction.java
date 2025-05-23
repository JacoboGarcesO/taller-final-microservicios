package com.example.transferservice.model;

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
public class Transaction {
  private Long id;
  private String type;
  private Long accountNumber;
  private Long bankId;
  private BigDecimal amount;
  private String description;
  private LocalDateTime timestamp;
}
