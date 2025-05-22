package com.axceldev.transferservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@Table(name = "transfers")
public class Transfer {
    @Id
    private Long TransferId;
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private Double amount;
    private Currency currency;
    private TransferStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
