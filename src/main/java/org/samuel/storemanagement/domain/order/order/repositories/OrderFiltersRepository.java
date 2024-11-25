package org.samuel.storemanagement.domain.order.order.repositories;

import org.samuel.storemanagement.domain.order.order.dtos.OrderFiltersParams;
import org.samuel.storemanagement.domain.order.order.models.Order;

import java.util.List;

public interface OrderFiltersRepository {
    List<Order> findAllWithFilters(OrderFiltersParams filtersParams);
}
