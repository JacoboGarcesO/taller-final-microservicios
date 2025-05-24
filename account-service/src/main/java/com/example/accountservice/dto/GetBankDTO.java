package com.example.accountservice.dto;

public class GetBankDTO {
    private Long id;
    private String name;
    private String address;

    public GetBankDTO(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public GetBankDTO() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
