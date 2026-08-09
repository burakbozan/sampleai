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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderAuthorityIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    private String tokenForRole(String role) {
        return Jwts.builder().setSubject("admin").claim("roles", role).signWith(SignatureAlgorithm.HS256, "secret".getBytes()).compact();
    }

    @Test
    public void updateStatusRequiresAdmin() throws Exception {
        String userToken = tokenForRole("USER");
        OrderDto req = new OrderDto(null, "CUST-AUTH-1","[]", null);
        String body = mapper.writeValueAsString(req);

        // create order
        String response = mvc.perform(post("/api/orders").header("Authorization", "Bearer " + userToken).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        OrderDto created = mapper.readValue(response, OrderDto.class);

        // attempt update status with USER -> 403
        OrderDto patch = new OrderDto(null, null, null, "SHIPPED");
        mvc.perform(patch("/api/orders/" + created.id + "/status").header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(patch))).andExpect(status().isForbidden());

        // with ADMIN -> 200
        String adminToken = tokenForRole("ADMIN");
        mvc.perform(patch("/api/orders/" + created.id + "/status").header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(patch))).andExpect(status().isOk());
    }
}
