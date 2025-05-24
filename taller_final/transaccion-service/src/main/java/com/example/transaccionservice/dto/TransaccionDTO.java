package com.example.transaccionservice.dto;

import java.math.BigDecimal;

public class TransaccionDTO {
  private Long cuentaOrigen;
  private Long cuentaDestino;
  private BigDecimal monto;

  public TransaccionDTO() {
  }

  public TransaccionDTO(Long cuentaOrigen, Long cuentaDestino, BigDecimal monto) {
    this.cuentaOrigen = cuentaOrigen;
    this.cuentaDestino = cuentaDestino;
    this.monto = monto;
  }

  public Long getCuentaOrigen() {
    return cuentaOrigen;
  }

  public void setCuentaOrigen(Long cuentaOrigen) {
    this.cuentaOrigen = cuentaOrigen;
  }

  public Long getCuentaDestino() {
    return cuentaDestino;
  }

  public void setCuentaDestino(Long cuentaDestino) {
    this.cuentaDestino = cuentaDestino;
  }

  public BigDecimal getMonto() {
    return monto;
  }

  public void setMonto(BigDecimal monto) {
    this.monto = monto;
  }
}
