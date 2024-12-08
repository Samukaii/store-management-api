package org.samuel.storemanagement.domain.order.item.repositories;

import org.samuel.storemanagement.domain.analytics.dtos.OrderItemSelling;
import org.samuel.storemanagement.domain.order.item.dtos.OrderItemAutocomplete;
import org.samuel.storemanagement.domain.order.item.models.OrderItem;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface OrderItemRepositoryCustom {
    List<OrderItemSelling> getBestSelling(Specification<OrderItem> specification);
    List<OrderItemAutocomplete> autocomplete(Specification<OrderItem> specification);
}
