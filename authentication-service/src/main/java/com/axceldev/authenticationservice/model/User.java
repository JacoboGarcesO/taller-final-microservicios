package com.axceldev.authenticationservice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String role;
}
