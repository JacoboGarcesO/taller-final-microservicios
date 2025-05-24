package com.example.transferenciaservice.dto;

import java.math.BigDecimal;

public class CuentaDTO {
    private Long numeroCuenta;
    private Long bancoId;
    private String tipoCuenta;
    private BigDecimal saldo;
    private BigDecimal saldoSobregiro;
    private String estado;

    public Long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(Long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Long getBancoId() {
        return bancoId;
    }

    public void setBancoId(Long bancoId) {
        this.bancoId = bancoId;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getSaldoSobregiro() {
        return saldoSobregiro;
    }

    public void setSaldoSobregiro(BigDecimal saldoSobregiro) {
        this.saldoSobregiro = saldoSobregiro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public CuentaDTO(Long numeroCuenta, Long bancoId, String tipoCuenta, BigDecimal saldo, BigDecimal saldoSobregiro, String estado) {
        this.numeroCuenta = numeroCuenta;
        this.bancoId = bancoId;
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldo;
        this.saldoSobregiro = saldoSobregiro;
        this.estado = estado;


    }
}