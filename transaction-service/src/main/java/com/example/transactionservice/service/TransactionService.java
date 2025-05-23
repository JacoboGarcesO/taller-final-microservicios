package com.example.transactionservice.service;

import com.example.transactionservice.dto.AccountDTO;
import com.example.transactionservice.dto.TransferDTO;
import com.example.transactionservice.event.TransferEventPublisher;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.repository.ITransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final ITransactionRepository transactionRepository;
    private final AccountClient accountClient;
    private final TransferEventPublisher transferEventPublisher;

    public Flux<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    public Mono<Transaction> create(Transaction transaction) {
        transaction.setId(null);
        return transactionRepository.save(transaction);
    }

    public Mono<Void> makeTransaction(TransferDTO transfer) {

        BigDecimal amount = transfer.getAmount();

        return accountClient.getAccount(transfer.getSourceAccountId())
                .zipWith(accountClient.getAccount(transfer.getDestinationAccountId()))
                .flatMap(tuple -> {
                    AccountDTO source = tuple.getT1();
                    AccountDTO destination = tuple.getT2();

                    if (source.getBalance().compareTo(amount) < 0) {
                        return Mono.error(new RuntimeException("Insufficient funds"));
                    }

                    source.setBalance(source.getBalance().subtract(amount));

                    Transaction withdrawal = new Transaction(null, "RETIRO", source.getAccountNumber(), source.getBankId(), amount, null, LocalDateTime.now());
                    Transaction deposit = new Transaction(null, "DEPOSITO", destination.getAccountNumber(), destination.getBankId(), amount, null, LocalDateTime.now());

                    if (source.getBankId().equals(destination.getBankId())) {

                        destination.setBalance(destination.getBalance().add(amount));

                        withdrawal.setDescription("Transfer to account number " + String.format("%011d", destination.getAccountNumber()));
                        deposit.setDescription("Transfer from account number " + String.format("%011d", source.getAccountNumber()));

                        return Mono.when(
                                accountClient.updateAccount(source),
                                transactionRepository.save(withdrawal),
                                accountClient.updateAccount(destination),
                                transactionRepository.save(deposit)
                        ).then();
                    } else {

                        withdrawal.setDescription("Interbank transfer to account number " + String.format("%011d", destination.getAccountNumber()));
                        deposit.setDescription("Interbank transfer from account number " + String.format("%011d", source.getAccountNumber()));

                        return Mono.when(
                                accountClient.updateAccount(source),
                                transactionRepository.save(withdrawal),
                                Mono.fromRunnable(() -> transferEventPublisher.publishPurchaseMadeEvent(deposit))
                        ).then();
                    }
                });
    }

}
