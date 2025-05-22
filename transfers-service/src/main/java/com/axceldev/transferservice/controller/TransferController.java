package com.axceldev.transferservice.controller;

import com.axceldev.transferservice.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transfers")
public class TransferController {
    private final TransferService transferService;
}
