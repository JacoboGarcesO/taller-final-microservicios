package com.example.accountservice.service;

import com.example.accountservice.dto.AccountDTO;
import com.example.accountservice.dto.GetBankDTO;
import com.example.accountservice.model.Account;
import com.example.accountservice.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountService {
    private final IAccountRepository accountRepository;
    //private final IBankRepository bankRepository;
    private final WebClient.Builder webClientBuilder;
    private final String bankServiceUrl;

    public AccountService(IAccountRepository accountRepository, /*IBankRepository bankRepository,*/
                          WebClient.Builder webClientBuilder, @Value("${bank.service.url}") String bankServiceUrl) {
        this.accountRepository = accountRepository;
        //this.bankRepository = bankRepository;
        this.webClientBuilder = webClientBuilder;
        this.bankServiceUrl = bankServiceUrl;
    }

    public Flux<Account> getAll(){
        return accountRepository.findAll();
    }

    public Mono<Account> createAccount(AccountDTO accountDTO){
        return getBank(accountDTO.getBankId())
                .flatMap(bank -> {
                    if (bank.getId() == null){
                        return Mono.error(new RuntimeException("Bank not found"));
                    }

                    return accountRepository.save(new Account(null,accountDTO.getType(), accountDTO.getNumber(), accountDTO.getBankId(), accountDTO.getCustomerID(), null));
                });
    }

    private Mono<GetBankDTO> getBank(Long bankId){
        return webClientBuilder.build()
                .get()
                .uri(bankServiceUrl + "/api/banks/" + bankId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new RuntimeException("Bank not found")))
                .bodyToMono(GetBankDTO.class);
    }
}
