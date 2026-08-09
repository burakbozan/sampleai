package com.sampleai.order.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class JwtUtil {
    private final String secret;

    public JwtUtil(String secret) { this.secret = secret; }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String subjectFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            throw new SignatureException("Invalid token");
        }
    }
}
