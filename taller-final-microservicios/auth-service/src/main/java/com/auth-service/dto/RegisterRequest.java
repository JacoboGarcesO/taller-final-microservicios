package com.auth-service.dto;

public record RegisterRequest(String username, String password, String role) { }