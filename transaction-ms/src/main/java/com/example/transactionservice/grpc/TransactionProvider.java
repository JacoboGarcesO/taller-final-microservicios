package com.example.transactionservice.grpc;

import com.example.transactionservice.repository.ITransactionRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

import java.util.stream.Collectors;

@GrpcService
@RequiredArgsConstructor
public class TransactionProvider extends TransactionServiceGrpc.TransactionServiceImplBase {

    private final ITransactionRepository transactionRepository;

    @Override
    public void getTransactionsByAccountNumber(AccountRequest request, StreamObserver<TransactionsResponse> responseObserver) {
        TransactionsResponse response = TransactionsResponse.newBuilder()
                .addAllTransactions(
                        transactionRepository.findByAccountNumber(request.getAccountNumber())
                                .map(transaction -> Transaction.newBuilder()
                                        .setId(transaction.getId())
                                        .setType(transaction.getType())
                                        .setAccountNumber(transaction.getAccountNumber())
                                        .setBankId(transaction.getBankId())
                                        .setAmount(transaction.getAmount().doubleValue())
                                        .setDescription(transaction.getDescription())
                                        .setTimestamp(transaction.getTimestamp().toString())
                                        .build())
                                .collect(Collectors.toList())
                                .block()
                ).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
