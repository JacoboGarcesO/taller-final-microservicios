package com.example.bancoservice.controller;

import com.example.bancoservice.controller.dto.BancoDTO;
import com.example.bancoservice.model.Banco;
import com.example.bancoservice.service.BancoService;
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
@RequestMapping("/api/bancos")
public class BancoController {

  private final BancoService bancoService;

  public BancoController(BancoService bancoService) {
    this.bancoService = bancoService;
  }

  @GetMapping
  public Flux<Banco> getAll() {
    return bancoService.getAll();
  }

  @GetMapping("/{bancoId}")
  public Mono<Banco> getById(@PathVariable Long bancoId) {
    return bancoService.getById(bancoId);
  }

  @PostMapping
  public Mono<Banco> create(@RequestBody BancoDTO bancoDTO) {
    Banco banco = new Banco(null, bancoDTO.getName(), bancoDTO.getDescription(), bancoDTO.getCountry());
    return bancoService.create(banco);
  }

  @PutMapping
  public Mono<Banco> update(@RequestBody Banco banco) {
    return bancoService.update(banco);
  }

}
