package com.example.account.grpc;

import com.example.account.dto.TransactionDTO;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;
import transactions.TransactionServiceGrpc;
import transactions.TransactionServiceOuterClass;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionGrpcClient {

    private final TransactionServiceGrpc.TransactionServiceBlockingStub stub;

    public TransactionGrpcClient() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("transaction-service", 9090)
                .usePlaintext()
                .build();
        stub = TransactionServiceGrpc.newBlockingStub(channel);
    }

    public List<TransactionDTO> getTransactionHistory(String accountId) {
        TransactionServiceOuterClass.TransactionRequest request =
                TransactionServiceOuterClass.TransactionRequest.newBuilder()
                        .setAccountId(accountId)
                        .build();

        TransactionServiceOuterClass.TransactionResponse response = stub.getTransactions(request);

        return response.getTransactionsList().stream().map(t -> {
            TransactionDTO dto = new TransactionDTO();
            dto.setAmount(t.getAmount());
            dto.setType(t.getType());
            return dto;
        }).collect(Collectors.toList());
    }
}