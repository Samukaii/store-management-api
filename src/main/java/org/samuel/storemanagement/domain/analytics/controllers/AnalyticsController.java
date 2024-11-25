package org.samuel.storemanagement.domain.analytics.controllers;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.analytics.dtos.OrderByPeriodResponse;
import org.samuel.storemanagement.domain.analytics.dtos.ProductBestSellingResponse;
import org.samuel.storemanagement.domain.order.order.dtos.OrderFiltersParams;
import org.samuel.storemanagement.domain.order.order.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("analytics")
public class AnalyticsController {
    private final OrderService orderService;

    @GetMapping("/best_selling_products")
    public ResponseEntity<List<ProductBestSellingResponse>> getBestSelling(@ModelAttribute OrderFiltersParams params) {
        return ResponseEntity.ok().body(orderService.getBestSellingProducts(params));
    }

    @GetMapping("/orders_by_period")
    public ResponseEntity<List<OrderByPeriodResponse>> getOrders() {
        return ResponseEntity.ok().body(orderService.findAllByDay());
    }
}

