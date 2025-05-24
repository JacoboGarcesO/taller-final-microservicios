package com.example.transactionservice.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransferRequest implements Serializable {
    private Long sourceAccountId;
    private Long destinationAccountId;
    private BigDecimal amount;
    private Long sourceBankId;
    private Long destinationBankId;

    public TransferRequest(Long sourceAccountId, Long destinationAccountId, BigDecimal amount, Long sourceBankId, Long destinationBankId) {
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
        this.sourceBankId = sourceBankId;
        this.destinationBankId = destinationBankId;
    }

    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(Long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public Long getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(Long destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getSourceBankId() {
        return sourceBankId;
    }

    public void setSourceBankId(Long sourceBankId) {
        this.sourceBankId = sourceBankId;
    }

    public Long getDestinationBankId() {
        return destinationBankId;
    }

    public void setDestinationBankId(Long destinationBankId) {
        this.destinationBankId = destinationBankId;
    }
}