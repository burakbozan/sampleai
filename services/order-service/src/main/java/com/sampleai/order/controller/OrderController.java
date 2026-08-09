package com.sampleai.order.controller;

import com.sampleai.order.dto.OrderDto;
import com.sampleai.order.domain.Order;
import com.sampleai.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<OrderDto> create(@RequestBody OrderDto req) {
        Order created = service.createOrder(req.customerId, req.itemsJson);
        OrderDto dto = new OrderDto(created.getId(), created.getCustomerId(), created.getItemsJson(), created.getStatus());
        return ResponseEntity.created(URI.create("/api/orders/" + dto.id)).body(dto);
    }

    @GetMapping
    public List<OrderDto> list() {
        return service.listOrders().stream().map(o -> new OrderDto(o.getId(), o.getCustomerId(), o.getItemsJson(), o.getStatus())).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> get(@PathVariable Long id) {
        return service.findById(id).map(o -> ResponseEntity.ok(new OrderDto(o.getId(), o.getCustomerId(), o.getItemsJson(), o.getStatus()))).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderDto> updateStatus(@PathVariable Long id, @RequestBody OrderDto req) {
        return service.findById(id).map(o -> {
            o.setStatus(req.status);
            Order updated = service.save(o);
            return ResponseEntity.ok(new OrderDto(updated.getId(), updated.getCustomerId(), updated.getItemsJson(), updated.getStatus()));
        }).orElse(ResponseEntity.notFound().build());
    }
}
