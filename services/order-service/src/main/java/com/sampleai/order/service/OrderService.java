package com.sampleai.order.service;

import com.sampleai.order.domain.Order;
import com.sampleai.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) { this.repository = repository; }

    public Order createOrder(String customerId, String itemsJson) {
        Order o = new Order(customerId, itemsJson);
        return repository.save(o);
    }

    public Optional<Order> findById(Long id) { return repository.findById(id); }
    public List<Order> listOrders() { return repository.findAll(); }
}
