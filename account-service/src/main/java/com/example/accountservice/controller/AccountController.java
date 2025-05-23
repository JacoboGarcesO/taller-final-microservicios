package com.example.accountservice.controller;

import com.example.accountservice.dto.AccountStrDTO;
import com.example.accountservice.dto.TransactionStrDTO;
import com.example.accountservice.model.Account;
import com.example.accountservice.service.AccountService;
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
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
  private final AccountService accountService;

  @GetMapping
  public Flux<AccountStrDTO> getAll() {
    return accountService.getAll()
            .map(AccountStrDTO::new);
  }

  @GetMapping("/{accountNumber}")
  public Mono<AccountStrDTO> getById(@PathVariable Long accountNumber) {
    return accountService.getById(accountNumber)
            .map(AccountStrDTO::new);
  }

  @PostMapping
  public Mono<AccountStrDTO> create(@RequestBody Account account) {
    return accountService.create(account)
            .map(AccountStrDTO::new);
  }

  @PutMapping
  public Mono<AccountStrDTO> update(@RequestBody Account account) {
    return accountService.update(account)
            .map(AccountStrDTO::new);
  }

  @GetMapping("/movements/{accountNumber}")
  public Flux<TransactionStrDTO> getMovements(@PathVariable Long accountNumber) {
    return accountService.getMovements(accountNumber)
            .map(TransactionStrDTO::new);
  }
}
