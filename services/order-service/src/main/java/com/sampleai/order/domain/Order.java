package com.sampleai.order.domain;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerId;
    private OffsetDateTime createdAt;
    private String status;

    // For simplicity store JSON payload or keep minimal fields; in real DDD use value objects and order lines
    @Lob
    private String itemsJson;

    protected Order() {}

    public Order(String customerId, String itemsJson) {
        this.customerId = customerId;
        this.itemsJson = itemsJson;
        this.createdAt = OffsetDateTime.now();
        this.status = "CREATED";
    }

    public Long getId() { return id; }
    public String getCustomerId() { return customerId; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public String getStatus() { return status; }
    public String getItemsJson() { return itemsJson; }

    public void setStatus(String status) { this.status = status; }
}
