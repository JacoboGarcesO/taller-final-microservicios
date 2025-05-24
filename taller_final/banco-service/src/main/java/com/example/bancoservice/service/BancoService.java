package com.example.bancoservice.service;

import com.example.bancoservice.model.Banco;
import com.example.bancoservice.repository.IBancoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BancoService {

    private final IBancoRepository bancoRepository;

    public BancoService(IBancoRepository bancoRepository) {
        this.bancoRepository = bancoRepository;
    }

    public Flux<Banco> getAll() {
        return bancoRepository.findAll();
    }

    public Mono<Banco> getById(Long bancoId) {
        return bancoRepository.findById(bancoId)
                .switchIfEmpty(Mono.error(new RuntimeException("El banco no está registrado")));
    }

    public Mono<Banco> create(Banco banco) {
        banco.setId(null); // Para evitar que se intente hacer un update
        return bancoRepository.save(banco);
    }

    public Mono<Banco> update(Banco banco) {
        return getById(banco.getId())
                .flatMap(existingBanco -> {
                    existingBanco.setName(banco.getName());
                    existingBanco.setDescription(banco.getDescription());
                    existingBanco.setCountry(banco.getCountry());
                    return bancoRepository.save(existingBanco);
                });
    }
}