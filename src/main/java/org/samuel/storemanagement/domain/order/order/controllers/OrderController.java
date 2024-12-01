package org.samuel.storemanagement.domain.order.order.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.samuel.storemanagement.domain.order.order.dtos.OrderResponse;
import org.samuel.storemanagement.domain.order.order.mappers.OrderMapper;
import org.samuel.storemanagement.domain.order.order.services.OrderService;
import org.samuel.storemanagement.general.dto.FilePayload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @SneakyThrows
    public ResponseEntity<?> importOrders(@RequestBody @ModelAttribute FilePayload payload) {
        service.importOrders(payload);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @SneakyThrows
    public ResponseEntity<List<OrderResponse>> getAll(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok().body(mapper.toListDto(service.findAll(params)));
    }

    @GetMapping("/{id}")
    @SneakyThrows
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.mapper.toDto(service.findById(id)));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
