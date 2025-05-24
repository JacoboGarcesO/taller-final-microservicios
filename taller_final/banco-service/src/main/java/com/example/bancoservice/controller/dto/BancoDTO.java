package com.example.bancoservice.controller.dto;

public class BancoDTO {
  private String name;
  private String description;
  private String country;

  public BancoDTO() {
  }

  public BancoDTO(String country, String name, String description) {
    this.country = country;
    this.name = name;
    this.description = description;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}