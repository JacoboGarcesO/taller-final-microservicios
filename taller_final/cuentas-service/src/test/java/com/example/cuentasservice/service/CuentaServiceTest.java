package com.example.cuentasservice.service;

import com.example.cuentasservice.grpc.Consumidor;
import com.example.cuentasservice.model.Cuenta;
import com.example.cuentasservice.repository.ICuentaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaServiceTest {

    private final ICuentaRepository cuentaRepository;
    private final GetBanco getBanco;
    private final Consumidor consumidor;
    private final CuentaService cuentaService;
    private static final Cuenta CUENTA_TEST = new Cuenta(123L, 1L, "ahorros", new BigDecimal(50000.0000), new BigDecimal(2000.0000), "A");
    public CuentaServiceTest() {
        cuentaRepository=Mockito.mock(ICuentaRepository.class);
        getBanco=Mockito.mock(GetBanco.class);
        consumidor=Mockito.mock(Consumidor.class);
        cuentaService=new CuentaService(cuentaRepository, getBanco, consumidor);
    }

    @Test
    void getAll() {
        Mockito.when(cuentaRepository.findAll()).thenReturn(Flux.just(CUENTA_TEST));
        StepVerifier.create(cuentaService.getAll()).expectNext(CUENTA_TEST).verifyComplete();
        Mockito.verify(cuentaRepository).findAll();

    }

    @Test
    void getById() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void getMovements() {
    }
}