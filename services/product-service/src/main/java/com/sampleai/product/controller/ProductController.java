package com.sampleai.product.controller;

import com.sampleai.product.dto.ProductDto;
import com.sampleai.product.domain.Product;
import com.sampleai.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) { this.service = service; }

    @GetMapping
    public List<ProductDto> list() {
        return service.listProducts().stream()
                .map(p -> new ProductDto(p.getId(), p.getSku(), p.getName(), p.getDescription(), p.getPrice()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> get(@PathVariable Long id) {
        return service.findById(id)
                .map(p -> ResponseEntity.ok(new ProductDto(p.getId(), p.getSku(), p.getName(), p.getDescription(), p.getPrice())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto req) {
        Product created = service.createProduct(req.sku, req.name, req.description, req.price);
        ProductDto dto = new ProductDto(created.getId(), created.getSku(), created.getName(), created.getDescription(), created.getPrice());
        return ResponseEntity.created(URI.create("/api/products/" + dto.id)).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
