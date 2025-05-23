package com.example.transfer.listener;

import com.example.transfer.dto.TransferRequestDTO;
import com.example.transfer.service.TransferService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferListener {

    @Autowired
    private TransferService transferService;

    @RabbitListener(queues = "interbank.transfer.queue")
    public void handleTransfer(TransferRequestDTO dto) {
        transferService.processTransfer(dto);
    }
}