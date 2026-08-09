package com.sampleai.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sampleai.order.dto.OrderDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderSecurityIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void postWithoutTokenReturnsUnauthorized() throws Exception {
        OrderDto req = new OrderDto(null, "CUST-SEC-1","[]", null);
        mvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void postWithValidTokenReturnsCreated() throws Exception {
        String token = Jwts.builder().setSubject("testuser").claim("roles","USER").signWith(SignatureAlgorithm.HS256, "secret".getBytes()).compact();
        OrderDto req = new OrderDto(null, "CUST-SEC-2","[]", null);
        mvc.perform(post("/api/orders").header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated());
    }
}
