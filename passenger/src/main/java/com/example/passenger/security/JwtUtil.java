package com.example.passenger.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    private Key key;
    private final long EXPIRATION = 86400000L; // 1 day in ms

    @jakarta.annotation.PostConstruct
    public void init() {
        // Generate a 256-bit (32-byte) secret key using SecureRandom
        byte[] secretBytes = new byte[32];
        new SecureRandom().nextBytes(secretBytes);
        key = Keys.hmacShaKeyFor(secretBytes);
        System.out.println("JWT Secret Key Generated: " + Base64.getEncoder().encodeToString(secretBytes)); // for dev/test only
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
