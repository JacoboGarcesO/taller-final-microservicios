package com.example.transactionservice.controller;

import com.example.transactionservice.dto.TransactionStrDTO;
import com.example.transactionservice.dto.TransferDTO;
import com.example.transactionservice.dto.TransferResponseDTO;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/transfer")
@RequiredArgsConstructor
public class TransactionController {
  private final TransactionService transactionService;

  @GetMapping
  public Flux<TransactionStrDTO> getAll() {
    return transactionService.getAll()
            .map(TransactionStrDTO::new);
  }

  @PostMapping("/create")
  public Mono<Transaction> create(@RequestBody Transaction transaction) {
    return transactionService.create(transaction);
  }

  @PostMapping
  public Mono<TransferResponseDTO> transfer(@RequestBody TransferDTO transfer) {
    return transactionService.makeTransaction(transfer)
            .then(Mono.just(new TransferResponseDTO()));
  }

}
