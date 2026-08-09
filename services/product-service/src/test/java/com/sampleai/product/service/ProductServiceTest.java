package com.sampleai.product.service;

import com.sampleai.product.domain.Product;
import com.sampleai.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ProductServiceTest {
    private ProductRepository repo;
    private ProductService service;

    @BeforeEach
    public void setUp() {
        repo = Mockito.mock(ProductRepository.class);
        service = new ProductService(repo);
    }

    @Test
    public void createProduct_savesAndReturns() {
        when(repo.save(any(Product.class))).thenAnswer(i -> {
            Product p = (Product) i.getArgument(0);
            // emulate id generation
            return p;
        });

        Product p = service.createProduct("SKU1", "Name", "Desc", new BigDecimal("9.99"));
        assertNotNull(p);
        assertEquals("SKU1", p.getSku());
        assertEquals("Name", p.getName());
    }

    @Test
    public void findById_forwardedToRepo() {
        Product p = new Product("SKU2","N","D", new BigDecimal("1.00"));
        when(repo.findById(1L)).thenReturn(Optional.of(p));
        Optional<Product> res = service.findById(1L);
        assertTrue(res.isPresent());
    }
}
