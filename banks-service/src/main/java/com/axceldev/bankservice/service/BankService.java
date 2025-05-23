package com.axceldev.bankservice.service;

import com.axceldev.bankservice.dto.CreateBankRequest;
import com.axceldev.bankservice.model.Bank;
import com.axceldev.bankservice.repository.IBankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@Service
public class BankService {

    private static final Logger logger = LoggerFactory.getLogger(BankService.class);

    private final IBankRepository bankRepository;


    public Mono<Object> createBank(CreateBankRequest request) {
        return bankRepository.findByName(request.name())
                .flatMap(existingBank -> Mono.error(new RuntimeException("Bank already exists")))
                .switchIfEmpty(Mono.defer(() -> {
                    Bank bank = Bank.builder()
                            .code(request.code())
                            .name(request.name())
                            .build();
                    return bankRepository.save(bank);
                }));
    }

    public Flux<Bank> getBanks() {
        return bankRepository.findAll()
                .onErrorResume( throwable -> {
                    logger.error("Error fetching banks", throwable);
                    return Flux.empty();
                });
    }

    public Mono<Boolean> existsBank(Long id) {
        return bankRepository.existsById(id)
                .flatMap(exists -> exists
                        ? Mono.just(true)
                        : Mono.just(false)
                ).onErrorResume(throwable -> {
                    logger.error("Error checking if bank exists", throwable);
                    return Mono.just(false);
                });
    }


}
