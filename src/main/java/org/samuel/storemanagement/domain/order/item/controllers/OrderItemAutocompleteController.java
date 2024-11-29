package org.samuel.storemanagement.domain.order.item.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.samuel.storemanagement.domain.order.item.dtos.OrderItemResponse;
import org.samuel.storemanagement.domain.order.item.mappers.OrderItemMapper;
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
    private final OrderItemMapper mapper;

    @GetMapping("/autocomplete")
    @SneakyThrows
    public ResponseEntity<List<OrderItemResponse>> autocomplete(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok().body(mapper.toListDto(service.autocomplete(filters)));
    }
}
