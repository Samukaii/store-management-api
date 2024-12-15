package org.samuel.storemanagement.domain.order.item.controllers;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.order.item.dtos.OrderItemResponse;
import org.samuel.storemanagement.domain.order.item.exceptions.OrderItemNotFoundException;
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
    public ResponseEntity<List<OrderItemResponse>> getAll(@PathVariable Long orderId) {
        return ResponseEntity.ok().body(mapper.toListDto(service.findAll(orderId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponse> getById(@PathVariable Long orderId, @PathVariable Long id) throws OrderItemNotFoundException {
        return ResponseEntity.ok().body(mapper.toDto(service.findById(orderId, id)));
    }
}
