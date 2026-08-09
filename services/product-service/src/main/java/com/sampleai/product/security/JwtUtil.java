package com.sampleai.product.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

import java.util.Date;

public class JwtUtil {
    private final String secret;

    public JwtUtil(String secret) { this.secret = secret; }

    public String subjectFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            throw new SignatureException("Invalid token");
        }
    }

    public String rolesFromToken(String token) {
        try {
            Object claim = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().get("roles");
            return claim == null ? "" : claim.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
