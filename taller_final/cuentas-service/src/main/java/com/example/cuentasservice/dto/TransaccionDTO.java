package com.example.cuentasservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransaccionDTO {


    private Long numeroRastreo;
    private String tipoCuenta;
    private Long banco;
    private BigDecimal cuenta;
    private String tipoTransaccion;
    private BigDecimal monto;
    private LocalDateTime fecha;

    public Long getNumeroRastreo() {
        return numeroRastreo;
    }

    public void setNumeroRastreo(Long numeroRastreo) {
        this.numeroRastreo = numeroRastreo;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Long getBanco() {
        return banco;
    }

    public void setBanco(Long banco) {
        this.banco = banco;
    }

    public BigDecimal getCuenta() {
        return cuenta;
    }

    public void setCuenta(BigDecimal cuenta) {
        this.cuenta = cuenta;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public TransaccionDTO(Long numeroRastreo, String tipoCuenta, Long banco, BigDecimal cuenta, String tipoTransaccion, BigDecimal monto, LocalDateTime fecha) {
        this.numeroRastreo = numeroRastreo;
        this.tipoCuenta = tipoCuenta;
        this.banco = banco;
        this.cuenta = cuenta;
        this.tipoTransaccion = tipoTransaccion;
        this.monto = monto;
        this.fecha = fecha;
    }
}


