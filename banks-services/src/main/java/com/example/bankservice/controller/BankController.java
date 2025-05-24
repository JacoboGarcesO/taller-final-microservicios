package com.example.bankservice.controller;

import com.example.bankservice.dto.NewBankDTO;
import com.example.bankservice.model.Bank;
import com.example.bankservice.service.BankService;
import org.springframework.http.ResponseEntity;
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
    public Flux<Bank> getAll(){
        return bankService.getAll();
    }
    @GetMapping("/{bankId}")
    public Mono<Bank> getById(@PathVariable Long bankId){
        return bankService.getById(bankId);
    }
    @PostMapping
    public Mono<Bank> newBank(@RequestBody NewBankDTO newBankDTO){
        return bankService.createBank(newBankDTO);
    }
    @PutMapping("/{bankId}")
    public Mono<Bank> editBank(@PathVariable Long bankId, @RequestBody Bank editBank){
        return bankService.editBank(bankId,editBank);
    }
}
