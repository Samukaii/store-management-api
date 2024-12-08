package org.samuel.storemanagement.domain.order.item.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.samuel.storemanagement.domain.order.item.dtos.OrderItemAutocomplete;
import org.samuel.storemanagement.domain.order.item.services.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("order-items")
public class OrderItemAutocompleteController {
    private final OrderItemService service;

    @GetMapping("/autocomplete")
    @SneakyThrows
    public ResponseEntity<List<OrderItemAutocomplete>> autocomplete(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok().body(service.autocomplete(filters));
    }
}
