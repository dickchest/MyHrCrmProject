package com.myhrcrmproject.service.auth;

import com.myhrcrmproject.service.validation.InvalidJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JwtTokenProviderExceptionsTest {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.lifetime}")
    private Long jwtLifeTime;

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        setField(jwtTokenProvider, "jwtSecret", jwtSecret);
        setField(jwtTokenProvider, "jwtLifeTime", jwtLifeTime);
    }

    @Test
    void validateTokenException_shouldThroughSignatureException() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUZXN0VXNlcjMiLCJpYXQiOjE3MDA4MTkxMjEsImV4cCI6MTcwMDgyNTEyMX0.RHS-fj2Gjug6_TFpxM60Dd_a0WsV5D_1J_t9XXEJpqM1";
        Exception exception = assertThrows(InvalidJwtException.class, () -> jwtTokenProvider.validateToken(token));
        assertEquals("Invalid JWT signature", exception.getMessage());
    }

    @Test
    void validateTokenException_shouldThroughIllegalArgumentException() {
        String token = "";
        Exception exception = assertThrows(InvalidJwtException.class, () -> jwtTokenProvider.validateToken(token));
        assertEquals("JWT claims string is empty", exception.getMessage());
    }

    @Test
    void validateTokenException_shouldThroughExpiredJwtException() {
        String token = Jwts.builder()
                .setSubject("TestName")
                .setIssuedAt(new Date(2022, 11, 11))
                .setExpiration(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
        Exception exception = assertThrows(InvalidJwtException.class, () -> jwtTokenProvider.validateToken(token));
        assertEquals("Expired JWT token", exception.getMessage());
    }

}
