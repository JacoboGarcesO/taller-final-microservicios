package com.example.bank.service;

import com.example.bank.model.Bank;
import com.example.bank.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

  @Autowired
  private BankRepository repository;

  public Mono<Bank> createBank(Bank bank) {
    return repository.save(bank);
  }

  public Flux<Bank> getAllBanks() {
    return repository.findAll();
  }

  public Mono<Bank> getBankById(String id) {
    return repository.findById(id);
  }

  public Mono<Bank> updateBank(String id, Bank updatedBank) {
    return repository.findById(id)
            .flatMap(existing -> {
              existing.setName(updatedBank.getName());
              existing.setCode(updatedBank.getCode());
              return repository.save(existing);
            });
  }
}