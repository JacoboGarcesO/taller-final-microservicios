package com.example.cuentasservice.repository;

import com.example.cuentasservice.model.Cuenta;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICuentaRepository extends ReactiveCrudRepository<Cuenta, Long> {
}
