package com.example.bankservice.service;

import com.example.bankservice.dto.NewBankDTO;
import com.example.bankservice.model.Bank;
import com.example.bankservice.repository.IBankRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class BankService {
    private final IBankRepository bankRepository;

    public BankService(IBankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Flux<Bank> getAll(){
        return bankRepository.findAll();
    }

    public Mono<Bank> getById(Long id){
        return bankRepository.findById(id)
                             .switchIfEmpty(Mono.just(new Bank()));
    }

    public Mono<Bank> createBank(NewBankDTO newBankDTO){
        Bank bank = new Bank(null, newBankDTO.getName(), newBankDTO.getCode(), newBankDTO.getAddress(),
                             newBankDTO.getPhone(), newBankDTO.getCountry());

        return bankRepository.save(bank);
    }

    public Mono<Bank> editBank(Long id, Bank editBank){
        return bankRepository.findById(id)
                .flatMap(existingBank -> {
                    existingBank.setName(editBank.getName());
                    existingBank.setAddress(editBank.getAddress());
                    existingBank.setCode(editBank.getCode());
                    existingBank.setCountry(editBank.getCountry());
                    existingBank.setPhone(editBank.getPhone());
                    existingBank.setCreated_at(LocalDateTime.now());
                    return  bankRepository.save(existingBank);
                });
    }
}
