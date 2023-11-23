package com.myhrcrmproject.service.auth;

import com.myhrcrmproject.service.validation.InvalidJwtException;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.security.SignatureException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;


class JwtTokenProviderTest {
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        setField(jwtTokenProvider, "jwtSecret", "984hg493gh0439rthr0429uruj2309yh937gc763fe87t3f89723gb");
        setField(jwtTokenProvider, "jwtLifeTime", 3600000L);
    }

    @Test
    void createToken() {
        String token = jwtTokenProvider.createToken("testUser");
        assertNotNull(token);
    }

    @Test
    void testGetUserNameFromJwt() {
        String token = jwtTokenProvider.createToken("testUser");
        String userName = jwtTokenProvider.getUserNameFromJwt(token);
        assertEquals("testUser", userName);
    }

    @Test
    void testValidateToken() {
        String token = jwtTokenProvider.createToken("testUser");
        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    void testInvalidJwtSignature() {
        JwtTokenProvider jwtTokenProviderMock = mock(JwtTokenProvider.class);
        when(jwtTokenProviderMock.validateToken(any())).thenThrow(InvalidJwtException.class);

        assertThrows(InvalidJwtException.class, () -> jwtTokenProviderMock.validateToken("invalid token"));
    }

    @Test
    void testMalformedJwtException() {
        JwtParser jwtParser = mock(JwtParser.class);
        doThrow(new MalformedJwtException("Invalid JWT token")).when(jwtParser).parseClaimsJws(anyString());

        assertThrows(InvalidJwtException.class, () -> jwtTokenProvider.validateToken("invalidToken"));
    }

    @Test
    void testExpiredJwtException() {
        JwtParser jwtParser = mock(JwtParser.class);
        doThrow(new UnsupportedJwtException("Unsupported JWT token")).when(jwtParser).parseClaimsJws(anyString());

        assertThrows(InvalidJwtException.class, () -> jwtTokenProvider.validateToken("Unsupported JWT token"));
    }

    @Test
    void testIllegalArgumentException() {
        JwtParser jwtParser = mock(JwtParser.class);
        doThrow(new IllegalArgumentException("JWT claims string is empty")).when(jwtParser).parseClaimsJws(anyString());

        assertThrows(InvalidJwtException.class, () -> jwtTokenProvider.validateToken("JWT claims string is empty"));
    }
}