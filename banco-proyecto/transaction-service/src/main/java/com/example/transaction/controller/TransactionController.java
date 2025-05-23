package com.example.transaction.controller;

import com.example.transaction.dto.TransferRequestDTO;
import com.example.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("api/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequestDTO dto) {
        transactionService.processTransfer(dto);
        return ResponseEntity.ok("Transfer processed successfully");
    }
}