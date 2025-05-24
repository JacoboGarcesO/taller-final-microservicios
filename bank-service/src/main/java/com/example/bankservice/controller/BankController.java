package com.example.bankservice.controller;

import com.example.bankservice.model.Bank;
import com.example.bankservice.service.BankService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/banks")
public class BankController {
    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping
    public Flux<Bank> getAll() {
        return bankService.getAll();
    }

    @GetMapping("/{bankId}")
    public Mono<Bank> getById(@PathVariable Long bankId) {
        return bankService.getById(bankId);
    }

    @PostMapping("/{nameBank}")
    public Mono<Bank> createBank(@PathVariable String nameBank) {
        return bankService.createBank(nameBank);
    }

    @PutMapping("/{bankId}")
    public Mono<Bank> update(@PathVariable Long bankId, @RequestBody Bank changeBank) {
        return bankService.updateBank(bankId, changeBank);
    }
}
