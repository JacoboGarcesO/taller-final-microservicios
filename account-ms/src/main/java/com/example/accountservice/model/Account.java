package com.example.accountservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table("accounts")
@Builder(toBuilder = true)
public class Account {
  @Id
  private Long accountNumber;
  private String accountType;
  private BigDecimal balance;
  private String status;
  private Long bankId;
}
