package com.example.transfer.repository;


import com.example.transfer.model.Transfer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransferRepository extends MongoRepository<Transfer, String> {}