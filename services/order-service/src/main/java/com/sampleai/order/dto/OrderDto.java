package com.sampleai.order.dto;

public class OrderDto {
    public Long id;
    public String customerId;
    public String itemsJson;
    public String status;

    public OrderDto() {}

    public OrderDto(Long id, String customerId, String itemsJson, String status) {
        this.id = id;
        this.customerId = customerId;
        this.itemsJson = itemsJson;
        this.status = status;
    }
}
