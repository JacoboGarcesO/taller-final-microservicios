package com.example.transaccionservice.controller;

import com.example.transaccionservice.dto.TransaccionDTO;
import com.example.transaccionservice.dto.RespuestaTransfDTO;
import com.example.transaccionservice.model.Transaccion;
import com.example.transaccionservice.service.TransaccionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/transaccion")
public class TransaccionController {
  private final TransaccionService transaccionService;

  public TransaccionController(TransaccionService transaccionService) {
    this.transaccionService = transaccionService;
  }

  @GetMapping
  public Flux<Transaccion> getAll() {
    return transaccionService.getAll();
  }

  @PostMapping("/create")
  public Mono<Transaccion> create(@RequestBody Transaccion transaccion) {
    return transaccionService.create(transaccion);
  }

  @PostMapping
  public Mono<RespuestaTransfDTO> trx(@RequestBody TransaccionDTO trx) {
    return transaccionService.makeTransaccion(trx)
            .then(Mono.just(new RespuestaTransfDTO()));
  }

}