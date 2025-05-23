package com.example.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TransferResponseDTO {
    private String status;

    public TransferResponseDTO() {
        this.status = "Transacción Exitosa";
    }
}

