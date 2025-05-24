package com.example.accountservice.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

@Component
public class TransactionConsumer {
    private ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
    private TransactionServiceGrpc.TransactionServiceBlockingStub blockingStub = TransactionServiceGrpc.newBlockingStub(channel);

    public TransactionList getTransaction(Long accountId) {
        TransactionRequest request = TransactionRequest.newBuilder()
                .setAccountId(accountId)
                .build();

        return blockingStub.getTransactions(request);
    }
}

