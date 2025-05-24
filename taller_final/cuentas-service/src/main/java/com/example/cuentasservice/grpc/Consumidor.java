package com.example.cuentasservice.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Consumidor {

    private final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
    private final TransaccionServiceGrpc.TransaccionServiceBlockingStub blockingStub = TransaccionServiceGrpc.newBlockingStub(channel);

    public List<Transaccion> getTransacciones(Long cuenta) {
        return blockingStub.getHistorialPorCuenta(
                        CuentaRequest.newBuilder()
                                .setCuenta(cuenta)
                                .build())
                .getTransaccionesList();
    }
}