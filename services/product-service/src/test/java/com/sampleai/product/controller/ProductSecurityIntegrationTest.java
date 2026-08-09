package com.sampleai.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sampleai.product.dto.ProductDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductSecurityIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void postWithoutTokenReturnsUnauthorized() throws Exception {
        ProductDto req = new ProductDto(null, "SKU-SEC-1","Name","Desc", new BigDecimal("1.00"));
        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void postWithValidTokenReturnsCreated() throws Exception {
        String token = Jwts.builder().setSubject("testuser").signWith(SignatureAlgorithm.HS256, "secret".getBytes()).compact();
        ProductDto req = new ProductDto(null, "SKU-SEC-2","Name","Desc", new BigDecimal("2.00"));
        mvc.perform(post("/api/products").header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated());
    }
}
