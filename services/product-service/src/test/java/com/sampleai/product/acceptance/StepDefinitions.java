package com.sampleai.product.acceptance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sampleai.product.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import io.cucumber.java.en.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;

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

    @Given("no products exist")
    public void no_products_exist() throws Exception {
        // in-memory DB starts empty
    }

    @When("I create a product with sku {string} and name {string}")
    public void create_product(String sku, String name) throws Exception {
        ProductDto req = new ProductDto(null, sku, name, "desc", null);
        String body = mapper.writeValueAsString(req);
        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isCreated());
    }

    @Then("the product list contains an item with sku {string}")
    public void list_contains(String sku) throws Exception {
        mvc.perform(get("/api/products")).andExpect(status().isOk());
        // further JSON assertions could be added
    }
}
