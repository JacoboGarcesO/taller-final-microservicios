package com.example.bancoservice.repository;

import com.example.bancoservice.model.Banco;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBancoRepository extends ReactiveCrudRepository<Banco, Long> {
}
