package com.sampleai.product.dto;

import java.math.BigDecimal;

public class ProductDto {
    public Long id;
    public String sku;
    public String name;
    public String description;
    public BigDecimal price;

    public ProductDto() {}

    public ProductDto(Long id, String sku, String name, String description, BigDecimal price) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
