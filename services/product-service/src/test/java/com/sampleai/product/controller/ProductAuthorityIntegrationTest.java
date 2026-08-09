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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductAuthorityIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    private String tokenForRole(String role) {
        return Jwts.builder().setSubject("admin").claim("roles", role).signWith(SignatureAlgorithm.HS256, "secret".getBytes()).compact();
    }

    @Test
    public void deleteRequiresAdminRole() throws Exception {
        String userToken = tokenForRole("USER");
        ProductDto req = new ProductDto(null, "SKU-AUTH-1","Name","Desc", new BigDecimal("1.00"));
        String body = mapper.writeValueAsString(req);

        // create with user token
        String response = mvc.perform(post("/api/products").header("Authorization", "Bearer " + userToken).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        ProductDto created = mapper.readValue(response, ProductDto.class);

        // attempt delete with USER -> 403 Forbidden
        mvc.perform(delete("/api/products/" + created.id).header("Authorization", "Bearer " + userToken))
                .andExpect(status().isForbidden());

        // delete with ADMIN -> 204 No Content
        String adminToken = tokenForRole("ADMIN");
        mvc.perform(delete("/api/products/" + created.id).header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNoContent());
    }
}
