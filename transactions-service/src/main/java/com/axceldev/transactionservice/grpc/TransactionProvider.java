package com.axceldev.transactionservice.grpc;

import com.axceldev.transactionservice.repository.ITransactionRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@GrpcService
public class TransactionProvider extends TransactionServiceGrpc.TransactionServiceImplBase {

    private final ITransactionRepository transactionRepository;

    @Override
    public void getTransactionHistory(TransactionHistoryRequest request, StreamObserver<TransactionHistoryResponse> responseObserver) {
        transactionRepository.findByAccountNumber(request.getAccountNumber())
                .map(trx -> Transaction.newBuilder()
                        .setTransactionId(trx.getTransactionId())
                        .setAccountNumber(trx.getAccountNumber())
                        .setAmount(trx.getAmount())
                        .setCreatedAt(trx.getCreatedAt().toString())
                        .setTransactionType(trx.getTransactionType().toString())
                        .build())
                .collectList()
                .map(transactions -> TransactionHistoryResponse.newBuilder()
                        .addAllTransactions(transactions)
                        .build())
                .defaultIfEmpty(TransactionHistoryResponse.newBuilder().build())
                .subscribe(
                        responseObserver::onNext,
                        responseObserver::onError,
                        responseObserver::onCompleted
                ).dispose();
    }

}
