package com.axceldev.accountservice.service;

import com.axceldev.accountservice.repository.IAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final IAccountRepository accountRepository;
}
