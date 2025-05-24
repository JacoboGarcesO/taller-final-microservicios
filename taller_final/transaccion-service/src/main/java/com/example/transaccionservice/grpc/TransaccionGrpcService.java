package com.example.transaccionservice.grpc;

import com.example.transaccionservice.repository.ITransaccionRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

import java.util.stream.Collectors;
@GrpcService
public class TransaccionGrpcService extends TransaccionServiceGrpc.TransaccionServiceImplBase {

    private final ITransaccionRepository iTransaccionRepository;

    public TransaccionGrpcService(ITransaccionRepository iTransaccionRepository) {
        this.iTransaccionRepository = iTransaccionRepository;
    }

    @Override
    public void getHistorialPorCuenta(CuentaRequest request, StreamObserver<HistorialResponse> responseObserver) {
        HistorialResponse response = HistorialResponse.newBuilder()
                .addAllTransacciones(
                        iTransaccionRepository.findByCuenta(request.getCuenta())
                                .map(transaccion -> Transaccion.newBuilder()
                                        .setNumeroRastreo(transaccion.getNumeroRastreo())
                                        .setTipoCuenta(transaccion.getTipoCuenta())
                                        .setBanco(transaccion.getBanco())
                                        .setCuenta(transaccion.getCuenta().doubleValue())
                                        .setMonto(transaccion.getMonto().longValue())
                                        .setFecha(transaccion.getFecha().toString())
                                        .build())
                                .collect(Collectors.toList())
                                .block()
                ).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}