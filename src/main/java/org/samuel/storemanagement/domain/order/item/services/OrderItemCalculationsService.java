package org.samuel.storemanagement.domain.order.item.services;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.order.item.models.OrderItem;
import org.samuel.storemanagement.domain.order.item.repositories.OrderItemRepository;
import org.samuel.storemanagement.domain.product.product.models.Product;
import org.samuel.storemanagement.domain.product.product.services.ProductsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemCalculationsService {
    private final ProductsService service;
    private final OrderItemRepository repository;

    public void updateAssociatedProduct(OrderItem item) {
        Product product = service.findByIntegrationName(item.getName()).orElse(null);

        item.setProduct(product);

        repository.save(item);
    }
}
