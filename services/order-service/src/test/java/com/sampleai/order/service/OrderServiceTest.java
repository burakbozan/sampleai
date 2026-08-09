package com.sampleai.order.service;

import com.sampleai.order.domain.Order;
import com.sampleai.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderServiceTest {
    private OrderRepository repo;
    private OrderService service;

    @BeforeEach
    public void setUp() {
        repo = Mockito.mock(OrderRepository.class);
        service = new OrderService(repo);
    }

    @Test
    public void createOrder_savesAndReturns() {
        when(repo.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));
        Order o = service.createOrder("C1", "[]");
        assertNotNull(o);
        assertEquals("C1", o.getCustomerId());
    }

    @Test
    public void findById_forwardedToRepo() {
        Order o = new Order("C2","[]");
        when(repo.findById(1L)).thenReturn(Optional.of(o));
        Optional<Order> res = service.findById(1L);
        assertTrue(res.isPresent());
    }
}
