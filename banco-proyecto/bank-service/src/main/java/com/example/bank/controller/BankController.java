package com.example.bank.controller;

import com.example.bank.model.Bank;
import com.example.bank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/banks")
public class BankController {

    @Autowired
    private BankService service;

    @PostMapping
    public Mono<ResponseEntity<Bank>> createBank(@RequestBody Bank bank) {
        return service.createBank(bank)
                .map(saved -> ResponseEntity.ok(saved));
    }

    @GetMapping
    public Flux<Bank> getAllBanks() {
        return service.getAllBanks();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Bank>> getBank(@PathVariable String id) {
        return service.getBankById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Bank>> updateBank(@PathVariable String id, @RequestBody Bank bank) {
        return service.updateBank(id, bank)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}