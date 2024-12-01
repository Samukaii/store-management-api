package org.samuel.storemanagement.domain.order.item.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.samuel.storemanagement.domain.analytics.dtos.OrderItemSelling;
import org.samuel.storemanagement.domain.order.item.dtos.OrderItemCreate;
import org.samuel.storemanagement.domain.order.item.events.OrderItemEventPublisher;
import org.samuel.storemanagement.domain.order.item.exceptions.OrderItemNotFoundException;
import org.samuel.storemanagement.domain.order.item.models.OrderItem;
import org.samuel.storemanagement.domain.order.item.repositories.OrderItemRepository;
import org.samuel.storemanagement.domain.order.order.models.Order;
import org.samuel.storemanagement.domain.product.product.models.Product;
import org.samuel.storemanagement.general.filters.FilterSpecificationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository repository;
    private final OrderItemEventPublisher publisher;
    private final FilterSpecificationService<OrderItem> specificationService;

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

    public List<OrderItemSelling> getBestSellingProducts(Map<String, String> params) {
        System.out.println(params);

        var specification = this.specificationService.buildSpecification(params);

        List<OrderItemSelling> orders = repository.getBestSelling(specification);

        List<OrderItemSelling> products = new ArrayList<>();

        orders.forEach(order -> {
            var existent = products.stream().filter(product -> product.getName().equals(order.getName()))
                    .findFirst();

            if (existent.isPresent()) {
                existent.get().setQuantity(existent.get().getQuantity() + order.getQuantity());
                existent.get().setTotal(existent.get().getTotal() + order.getTotal());

                return;
            }

            products.add(order);
        });

        return products;
    }

    public List<OrderItem> findAll(Long orderId) {
        return repository.findAllByOrderId(orderId);
    }

    public List<OrderItem> findAllByProduct(Product product) {
        return repository.findAllByName(product.getIntegrationName());
    }

    public List<OrderItem> autocomplete(Map<String, String> filters) {
        var specification = specificationService.buildSpecification(filters);

        return repository.findAll(specification);
    }

    public void save(OrderItem orderItem) {
        OrderItem result = repository.save(orderItem);

        publisher.emitChange(result);
    }
}
