package org.samuel.storemanagement.domain.order.item.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.samuel.storemanagement.domain.order.item.dtos.OrderItemCreate;
import org.samuel.storemanagement.domain.order.item.events.OrderItemEventPublisher;
import org.samuel.storemanagement.domain.order.item.exceptions.OrderItemNotFoundException;
import org.samuel.storemanagement.domain.order.item.models.OrderItem;
import org.samuel.storemanagement.domain.order.item.repositories.OrderItemRepository;
import org.samuel.storemanagement.domain.order.order.models.Order;
import org.samuel.storemanagement.domain.product.product.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository repository;
    private final OrderItemEventPublisher publisher;

    @SneakyThrows
    public void importOrderItems(Order order, List<OrderItemCreate> orderItems) {
        orderItems.forEach(item -> create(order, item));
    }

    @SneakyThrows
    private void create(Order order, OrderItemCreate payload) {
        var builder = OrderItem.builder();

        builder.name(payload.getName())
                .quantity(payload.getQuantity())
                .order(order)
                .total(payload.getTotal());

        save(builder.build());
    }

    @SneakyThrows
    public OrderItem findById(Long productFoodInputId, Long productId) {
        return repository.findByIdAndOrderId(productFoodInputId, productId).orElseThrow(OrderItemNotFoundException::new);
    }

    public List<OrderItem> findAll(Long orderId) {
        return repository.findAllByOrderId(orderId);
    }

    public List<OrderItem> findAllByProduct(Product product) {
        return repository.findAllByName(product.getIntegrationName());
    }

    public List<OrderItem> autocomplete(String search) {
        return repository.searchAllDistinctByName(search.toLowerCase());
    }

    public void save(OrderItem orderItem) {
        OrderItem result = repository.save(orderItem);

        publisher.emitChange(result);
    }
}
