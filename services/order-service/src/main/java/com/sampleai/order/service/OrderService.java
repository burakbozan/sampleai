package com.sampleai.order.service;

import com.sampleai.order.domain.Order;
import com.sampleai.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import com.sampleai.order.messaging.OrderEventPublisher;

@Service
@Transactional
public class OrderService {
    private final OrderRepository repository;
    private final OrderEventPublisher orderEventPublisher;

    public OrderService(OrderRepository repository, OrderEventPublisher orderEventPublisher) { 
        this.repository = repository; 
        this.orderEventPublisher = orderEventPublisher;
    }

    public Order createOrder(String customerId, String itemsJson) {
        Order o = new Order(customerId, itemsJson);
        Order saved = repository.save(o);
        // publish domain event - OrderCreated
        try {
            if (orderEventPublisher != null) orderEventPublisher.publishOrderCreated(saved);
        } catch (Exception e) {
            // non-blocking: log and continue
            System.err.println("Failed to publish order event: " + e.getMessage());
        }
        return saved;
    }

    public Optional<Order> findById(Long id) { return repository.findById(id); }
    public List<Order> listOrders() { return repository.findAll(); }

    public Order save(Order o) { return repository.save(o); }
}
