package com.sampleai.auth.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.List;

public class JwtUtil {
    // In real deployments read from env/config and rotate properly
    private final String secret;
    private final long validityMs = 3600_000; // 1h

    public JwtUtil(String secret) { this.secret = secret; }

    public String generateToken(String subject, List<String> roles) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(subject)
                .claim("roles", String.join(",", roles))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validityMs))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes()).compact();
    }
}
