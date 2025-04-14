package com.db.taskcrud.unit;

import com.db.taskcrud.infra.security.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private UserDetails userDetails;

    private final String secretKey = Base64.getEncoder().encodeToString("my-test-secret-key-which-is-long-enough".getBytes());

    @BeforeEach
    void setup(){
        tokenService.secret = secretKey;

        when(userDetails.getUsername()).thenReturn("Peter Parker");
    }

    @Test
    void testGenerateTokenAndValidate() {
        String token = tokenService.generateToken(userDetails);

        assertNotNull(token);
        assertTrue(tokenService.isTokenValid(token, userDetails));
    }

    @Test
    void testExtractUsername() {
        String token = tokenService.generateToken(userDetails);

        String username = tokenService.extractUsername(token);
        assertEquals("Peter Parker", username);
    }

    @Test
    void testTokenIsInvalidWithWrongUsername() {
        String token = tokenService.generateToken(userDetails);

        UserDetails otherUser = mock(UserDetails.class);
        when(otherUser.getUsername()).thenReturn("wronguser");

        assertFalse(tokenService.isTokenValid(token, otherUser));
    }

    @Test
    void testExpiredTokenIsInvalid() throws InterruptedException {
        TokenService shortLivedTokenService = new TokenService() {
            @Override
            public String generateToken(UserDetails userDetails) {
                return Jwts.builder()
                        .setSubject(userDetails.getUsername())
                        .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
                        .setExpiration(new java.util.Date(System.currentTimeMillis() + 1000)) // 1 segundo
                        .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret)), io.jsonwebtoken.SignatureAlgorithm.HS256)
                        .compact();
            }
        };
        shortLivedTokenService.secret = secretKey;

        String token = shortLivedTokenService.generateToken(userDetails);

        Thread.sleep(1500);

        assertThrows(ExpiredJwtException.class, () -> {
            shortLivedTokenService.isTokenValid(token, userDetails);
        });
    }
}
