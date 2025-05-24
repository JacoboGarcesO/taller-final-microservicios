package com.example.transaccionservice.dto;

public class RespuestaTransfDTO {
  private String status;

  public RespuestaTransfDTO() {
    this.status = "Transacción Exitosa";
  }

  // Constructor con parámetro
  public RespuestaTransfDTO(String status) {
    this.status = status;
  }

  // Getter
  public String getStatus() {
    return status;
  }

  // Setter
  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "TransferResponseDTO{" +
            "status='" + status + '\'' +
            '}';
  }
}
