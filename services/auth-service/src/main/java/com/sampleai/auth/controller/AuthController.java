package com.sampleai.auth.controller;

import com.sampleai.auth.repository.UserRepository;
import com.sampleai.auth.domain.User;
import com.sampleai.auth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository users;
    private final JwtUtil jwt;

    public AuthController(UserRepository users, @Value("${auth.jwt.secret:secret}") String secret) {
        this.users = users;
        this.jwt = new JwtUtil(secret);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String,String> body) {
        String username = body.get("username");
        String password = body.get("password");
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        User u = new User(username, hash);
        users.save(u);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> body) {
        String username = body.get("username");
        String password = body.get("password");
        return users.findByUsername(username).map(u -> {
            if (BCrypt.checkpw(password, u.getPasswordHash())) {
                // assign default role USER; in real app fetch from user store
                String token = jwt.generateToken(username, java.util.List.of("USER"));
                return ResponseEntity.ok(Map.of("token", token));
            } else return ResponseEntity.status(401).build();
        }).orElse(ResponseEntity.status(401).build());
    }
}
