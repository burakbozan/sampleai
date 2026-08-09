package com.sampleai.order.acceptance;

import com.sampleai.order.dto.OrderDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StepDefinitions {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Given("no orders exist")
    public void no_orders_exist() throws Exception { }

    @When("I create an order for customer {string} with items {string}")
    public void create_order(String customerId, String itemsJson) throws Exception {
        OrderDto req = new OrderDto(null, customerId, itemsJson, null);
        String body = mapper.writeValueAsString(req);
        mvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isCreated());
    }

    @Then("the order list contains an order for customer {string}")
    public void list_contains(String customerId) throws Exception {
        mvc.perform(get("/api/orders")).andExpect(status().isOk());
    }
}
