package com.axceldev.authenticationservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private final String secret = "mySuperSecretKeyForJwt1234567890123456";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        var secretField = JwtService.class.getDeclaredFields()[0];
        secretField.setAccessible(true);
        try {
            secretField.set(jwtService, secret);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void generateToken_shouldReturnValidJwt() {
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn("usuario");
        GrantedAuthority authority = () -> "ROLE_USER";
        List<GrantedAuthority> authorities = Collections.singletonList(authority);
        Mockito.when(authentication.getAuthorities()).thenReturn((Collection) authorities);
        String token = jwtService.generateToken(authentication);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals("usuario", claims.getSubject());
        assertTrue(((List<?>) claims.get("authorities")).contains("ROLE_USER"));
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

}