package com.bank-service.model;

import jakarta.persistence.*;

@Table(name = "banks")
public class Bank {

  @Id
  private Long id;

  private String name;
  private String country;
  private String code;

  public Bank() {
  }
  public Bank(Long id, String name, String country, String code) {
    this.id = id;
    this.name = name;
    this.country = country;
    this.code = code;
  }
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getCountry() {
    return country;
  }
  public void setCountry(String country) {
    this.country = country;
  }
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }

}