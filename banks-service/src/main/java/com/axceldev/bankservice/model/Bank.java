package com.axceldev.bankservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder(toBuilder = true)
@Table(name = "banks")
public class Bank {
    @Id
    private Long bankId;
    private String name;
    private String code;
}
