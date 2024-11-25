package org.samuel.storemanagement.domain.order.item.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.samuel.storemanagement.domain.order.item.dtos.OrderItemResponse;
import org.samuel.storemanagement.domain.order.item.mappers.OrderItemMapper;
import org.samuel.storemanagement.domain.order.item.services.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("orders/{orderId}/items")
public class OrderItemController {
    private final OrderItemService service;
    private final OrderItemMapper mapper;

    @GetMapping
    @SneakyThrows
    public ResponseEntity<List<OrderItemResponse>> getAll(@PathVariable Long orderId) {
        return ResponseEntity.ok().body(mapper.toListDto(service.findAll(orderId)));
    }

    @GetMapping("/{id}")
    @SneakyThrows
    public ResponseEntity<OrderItemResponse> getById(@PathVariable Long orderId, @PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.toDto(service.findById(orderId, id)));
    }
}
