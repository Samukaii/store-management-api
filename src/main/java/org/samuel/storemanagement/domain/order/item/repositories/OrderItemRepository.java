package org.samuel.storemanagement.domain.order.item.repositories;

import org.samuel.storemanagement.domain.order.item.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, JpaSpecificationExecutor<OrderItem> {
    Optional<OrderItem> findByIdAndOrderId(long productFoodInputId, long orderId);
    List<OrderItem> findAllByOrderId(long orderId);
    List<OrderItem> findAllByName(String name);
}
