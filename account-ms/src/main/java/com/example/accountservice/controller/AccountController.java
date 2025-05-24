package com.example.accountservice.controller;

import com.example.accountservice.dto.AccountStrDTO;
import com.example.accountservice.dto.TransactionStrDTO;
import com.example.accountservice.model.Account;
import com.example.accountservice.service.AccountManagementService;
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
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class AccountController {
  private final AccountManagementService accountManagementService;

  @GetMapping
  public Flux<AccountStrDTO> getAll() {
    return accountManagementService.getAll()
            .map(AccountStrDTO::new);
  }

  @GetMapping("/{numeroCuenta}")
  public Mono<AccountStrDTO> getById(@PathVariable Long numeroCuenta) {
    return accountManagementService.getById(numeroCuenta)
            .map(AccountStrDTO::new);
  }

  @PostMapping
  public Mono<AccountStrDTO> create(@RequestBody Account account) {
    return accountManagementService.create(account)
            .map(AccountStrDTO::new);
  }

  @PutMapping
  public Mono<AccountStrDTO> update(@RequestBody Account account) {
    return accountManagementService.update(account)
            .map(AccountStrDTO::new);
  }

  @GetMapping("/movimientos/{numeroCuenta}")
  public Flux<TransactionStrDTO> getMovements(@PathVariable Long numeroCuenta) {
    return accountManagementService.getMovements(numeroCuenta)
            .map(TransactionStrDTO::new);
  }
}
