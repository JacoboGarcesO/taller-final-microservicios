package com.axceldev.transferservice.service;

import com.axceldev.transferservice.repository.ITransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TransferService {
    private final ITransferRepository transferRepository;
}
