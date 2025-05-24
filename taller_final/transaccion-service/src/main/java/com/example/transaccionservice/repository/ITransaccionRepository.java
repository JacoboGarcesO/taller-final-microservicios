package com.example.transaccionservice.repository;

import com.example.transaccionservice.model.Transaccion;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ITransaccionRepository extends ReactiveCrudRepository<Transaccion, Long> {
    Flux<Transaccion> findByCuenta(Long cuenta);
}
