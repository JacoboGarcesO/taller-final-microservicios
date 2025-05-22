package com.axceldev.accountservice.grpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TransactionConsumer {

    @Value("${transaction.grpc.server.host}")
    private String grpcServerHost;

    @Value("${transaction.grpc.server.port}")
    private int grpcServerPort;

    private TransactionServiceGrpc.TransactionServiceBlockingStub blockingStub;

    @PostConstruct()
    public void init() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(grpcServerHost, grpcServerPort)
                .usePlaintext()
                .build();
        blockingStub = TransactionServiceGrpc.newBlockingStub(channel);
    }

    public TransactionHistoryResponse getTransactionHistory(String accountNumber) {
        TransactionHistoryRequest request = TransactionHistoryRequest.newBuilder()
                .setAccountNumber(accountNumber)
                .build();
        return blockingStub.getTransactionHistory(request);
    }
}
