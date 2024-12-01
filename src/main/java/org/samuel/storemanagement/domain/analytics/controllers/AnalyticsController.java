package org.samuel.storemanagement.domain.analytics.controllers;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.analytics.dtos.OrderByPeriodResponse;
import org.samuel.storemanagement.domain.analytics.dtos.OrderItemSelling;
import org.samuel.storemanagement.domain.order.item.services.OrderItemService;
import org.samuel.storemanagement.domain.order.order.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("analytics")
public class AnalyticsController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @GetMapping("/best_selling_products")
    public ResponseEntity<List<OrderItemSelling>> getBestSelling(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok().body(orderItemService.getBestSellingProducts(params));
    }

    @GetMapping("/orders_by_period")
    public ResponseEntity<List<OrderByPeriodResponse>> getOrders(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok().body(orderService.findAllByPeriod(params));
    }
}

