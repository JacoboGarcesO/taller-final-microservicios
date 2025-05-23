package com.example.bankservice.controller;

import com.example.bankservice.model.Bank;
import com.example.bankservice.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/banks")
@RequiredArgsConstructor
public class BankController {
  private final BankService bankService;

  @GetMapping
  public Flux<Bank> getAll() {
    return bankService.getAll();
  }

  @GetMapping("/{bankId}")
  public Mono<Bank> getById(@PathVariable Long bankId) {
    return bankService.getById(bankId);
  }

  @PostMapping
  public Mono<Bank> create(@RequestBody Bank bank) {
    return bankService.create(bank);
  }

  @PutMapping
  public Mono<Bank> update(@RequestBody Bank bank) {
    return bankService.update(bank);
  }

}
