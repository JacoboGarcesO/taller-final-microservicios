package com.example.transactionservice.grpc;

import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.repository.ITransactionRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;
import reactor.core.publisher.Mono;

import java.util.List;

@GrpcService
public class TransactionProvider extends TransactionServiceGrpc.TransactionServiceImplBase{
    private final ITransactionRepository repository;

    public TransactionProvider(ITransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void getTransactions(TransactionRequest request,
                                StreamObserver<TransactionList> responseObserver) {

        repository.findByAccountId(request.getAccountId())
                .collectList() // Mono<List<Transaction>>
                .map(transacciones -> {
                    List<TransactionResponse> grpcTransactions = transacciones.stream()
                            .map(tx -> TransactionResponse.newBuilder()
                                    .setId(tx.getId())
                                    .setAccountId(tx.getAccountId())
                                    .setType(tx.getType())
                                    .setAmount(tx.getAmount().doubleValue())
                                    .setDate(tx.getDate().toString())
                                    .build())
                            .toList();

                    return TransactionList.newBuilder()
                            .addAllTransactions(grpcTransactions)
                            .build();
                })
                .subscribe(response -> {
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                }, responseObserver::onError);
    }


}
