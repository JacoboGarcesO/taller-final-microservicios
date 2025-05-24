package com.example.transferservice.service;


import com.example.transferservice.dto.TransferRequest;
import com.example.transferservice.dto.TransactionDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransferService {
    private final SaveTransferClient saveTransfer;

    public TransferService(SaveTransferClient saveTransfer) {
        this.saveTransfer = saveTransfer;
    }

    public Mono<Void> registrarDepositoConImpuesto(TransferRequest request) {
        BigDecimal impuesto = new BigDecimal("0.004");
        BigDecimal neto = request.getAmount().subtract(request.getAmount().multiply(impuesto));

        TransactionDTO deposito = new TransactionDTO(
                null,
                request.getDestinationAccountId(),
                "deposito",
                neto,
                LocalDateTime.now()
        );

        return registrarTransaccion(deposito.getAccountId(),deposito.getAmount())
                .doOnNext(tx -> System.out.println("✅ Depósito con impuesto guardado: " + tx))
                .then();
    }

    private Mono<Void> registrarTransaccion(Long accountId, BigDecimal amount) {

        return saveTransfer.saveTransfer(accountId, amount)
                .doOnSuccess(v -> System.out.println("📝 Transacción registrada en transaction-service"))
                .doOnError(e -> System.err.println("❌ Error al registrar transacción: " + e.getMessage()));
    }
}

