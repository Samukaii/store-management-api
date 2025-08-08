package org.samuel.storemanagement.domain.order.order.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.order.order.dtos.OrderCreateDTO;
import org.samuel.storemanagement.domain.order.order.dtos.OrderImportedDTO;
import org.samuel.storemanagement.domain.order.order.dtos.OrderResponse;
import org.samuel.storemanagement.domain.order.order.exceptions.OrderNotFoundException;
import org.samuel.storemanagement.domain.order.order.mappers.OrderMapper;
import org.samuel.storemanagement.domain.order.order.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("orders")
public class OrderController {
    private final OrderService service;
    private final OrderMapper mapper;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody OrderCreateDTO payload) throws IOException {
        service.create(payload);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok().body(mapper.toListDto(service.findAll(params)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) throws OrderNotFoundException {
        return ResponseEntity.ok().body(this.mapper.toDto(service.findById(id)));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
