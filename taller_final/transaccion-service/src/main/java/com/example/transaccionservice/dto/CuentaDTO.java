package com.example.transaccionservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CuentaDTO {

    @JsonProperty("numeroCuenta")
    private Long numeroCuenta;

    @JsonProperty("bancoId")
    private Long bancoId;

    @JsonProperty("tipoCuenta")
    private String tipoCuenta;

    @JsonProperty("saldo")
    private BigDecimal saldo;

    @JsonProperty("saldoSobregiro")
    private BigDecimal saldoSobregiro;

    @JsonProperty("estado")
    private String estado;

    // Constructor
    public CuentaDTO(Long numeroCuenta, Long bancoId, String tipoCuenta, BigDecimal saldo, BigDecimal saldoSobregiro, String estado) {
        this.numeroCuenta = numeroCuenta;
        this.bancoId = bancoId;
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldo;
        this.saldoSobregiro = saldoSobregiro;
        this.estado = estado;
    }

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

    @Override
    public String toString() {
        return "CuentaDTO{" +
                "numeroCuenta=" + numeroCuenta +
                ", bancoId=" + bancoId +
                ", tipoCuenta='" + tipoCuenta + '\'' +
                ", saldo=" + saldo +
                ", saldoSobregiro=" + saldoSobregiro +
                ", estado='" + estado + '\'' +
                '}';
    }
}

//package com.example.transaccionservice.dto;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import java.math.BigDecimal;
//
//public class CuentaDTO {
//
//    @JsonProperty("numeroCuenta")
//    private Long numeroCuenta;
//
//    @JsonProperty("bancoId")
//    private Long bancoId;
//
//    @JsonProperty("tipoCuenta")
//    private String tipoCuenta;
//
//    @JsonProperty("saldo")
//    private BigDecimal saldo;
//
//    @JsonProperty("saldoSobregiro")
//    private BigDecimal saldoSobregiro;
//
//    @JsonProperty("estado")
//    private String estado;
//
//    // Constructor
//    public CuentaDTO(Long numeroCuenta, Long bancoId, String tipoCuenta, BigDecimal saldo, BigDecimal saldoSobregiro, String estado) {
//        this.numeroCuenta = numeroCuenta;
//        this.bancoId = bancoId;
//        this.tipoCuenta = tipoCuenta;
//        this.saldo = saldo;
//        this.saldoSobregiro = saldoSobregiro;
//        this.estado = estado;
//    }
//
//    public Long getNumeroCuenta() {
//        return numeroCuenta;
//    }
//
//    public void setNumeroCuenta(Long numeroCuenta) {
//        this.numeroCuenta = numeroCuenta;
//    }
//
//    public Long getBancoId() {
//        return bancoId;
//    }
//
//    public void setBancoId(Long bancoId) {
//        this.bancoId = bancoId;
//    }
//
//    public String getTipoCuenta() {
//        return tipoCuenta;
//    }
//
//    public void setTipoCuenta(String tipoCuenta) {
//        this.tipoCuenta = tipoCuenta;
//    }
//
//    public BigDecimal getSaldo() {
//        return saldo;
//    }
//
//    public void setSaldo(BigDecimal saldo) {
//        this.saldo = saldo;
//    }
//
//    public BigDecimal getSaldoSobregiro() {
//        return saldoSobregiro;
//    }
//
//    public void setSaldoSobregiro(BigDecimal saldoSobregiro) {
//        this.saldoSobregiro = saldoSobregiro;
//    }
//
//    public String getEstado() {
//        return estado;
//    }
//
//    public void setEstado(String estado) {
//        this.estado = estado;
//    }
//}


