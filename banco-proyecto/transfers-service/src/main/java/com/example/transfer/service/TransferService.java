package com.example.transaction.service;

import com.example.transfer.dto.TransferRequestDTO;
import com.example.transfer.model.Transfer;
import com.example.transfer.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransferService {

    private static final BigDecimal TAX_RATE = new BigDecimal("0.005"); // 0.5%

    @Autowired
    private TransaferRepository transferRepository;

    public void processTransfer(TransferRequestDTO dto) {
        BigDecimal tax = dto.getAmount().multiply(TAX_RATE);
        BigDecimal finalAmount = dto.getAmount().subtract(tax);

        Transaction deposit = new Transaction(
                dto.getDestinationAccountId(),
                finalAmount,
                LocalDateTime.now(),
                "DEPOSIT",
                dto.getDestinationBankId()
        );

        transferRepository.save(deposit);
    }
}