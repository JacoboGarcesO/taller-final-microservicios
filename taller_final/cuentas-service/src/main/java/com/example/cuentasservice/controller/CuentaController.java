package com.example.cuentasservice.controller;

import com.example.cuentasservice.model.Cuenta;
import com.example.cuentasservice.dto.CuentaDTO;
import com.example.cuentasservice.dto.TransaccionDTO;
import com.example.cuentasservice.service.CuentaService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

  private final CuentaService cuentaService;

  public CuentaController(CuentaService cuentaService) {
    this.cuentaService = cuentaService;
  }

  @GetMapping
  public Flux<Cuenta> getAll() {
    return cuentaService.getAll();
  }

  @GetMapping("/{cuentaId}")
  public Mono<Cuenta> getById(@PathVariable Long cuentaId) {
    return cuentaService.getById(cuentaId);
  }

  @PostMapping
  public Mono<Cuenta> create(@RequestBody CuentaDTO cuentaDTO) {
    return cuentaService.create(cuentaDTO);
  }

  @PutMapping
  public Mono<Cuenta> update(@RequestBody Cuenta cuenta) {
    return cuentaService.update(cuenta);
  }

  @GetMapping("/movements/{accountId}")
  public Flux<TransaccionDTO> getMovements(@PathVariable Long accountId) {
    return cuentaService.getMovements(accountId);
  }
}
