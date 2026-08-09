package com.sampleai.product.service;

import com.sampleai.product.domain.Product;
import com.sampleai.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product createProduct(String sku, String name, String description, BigDecimal price) {
        Product p = new Product(sku, name, description, price);
        return repository.save(p);
    }

    public Optional<Product> findById(Long id) { return repository.findById(id); }
    public Optional<Product> findBySku(String sku) { return repository.findBySku(sku); }
    public List<Product> listProducts() { return repository.findAll(); }
}
