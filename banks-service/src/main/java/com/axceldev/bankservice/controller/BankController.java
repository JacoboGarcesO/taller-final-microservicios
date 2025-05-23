package com.axceldev.bankservice.controller;

import com.axceldev.bankservice.dto.CreateBankRequest;
import com.axceldev.bankservice.model.Bank;
import com.axceldev.bankservice.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/banks")
public class BankController {

    private final BankService bankService;

    @PostMapping()
    public Mono<Object> createBank(@RequestBody CreateBankRequest Request) {
        return bankService.createBank(Request)
                .map(bank -> bank)
                .onErrorResume(Mono::error);
    }

    @GetMapping()
    public Flux<Bank> getBanks() {
        return bankService.getBanks()
                .map(bank -> bank)
                .onErrorResume(Mono::error);
    }

    @GetMapping("/{id}")
    public Mono<Boolean> existsBank(@PathVariable Long id) {
        return bankService.existsBank(id)
                .map(exists -> exists)
                .onErrorResume(Mono::error);
    }

}
