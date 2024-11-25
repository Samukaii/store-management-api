package org.samuel.storemanagement.domain.order.item.repositories;

import org.samuel.storemanagement.domain.order.item.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByIdAndOrderId(long productFoodInputId, long orderId);
    List<OrderItem> findAllByOrderId(long orderId);

    @Query("SELECT item FROM OrderItem item WHERE item.id IN " +
            "(SELECT MIN(subItem.id) FROM OrderItem subItem GROUP BY subItem.name) " +
            "AND (lower(item.name)) like %?1% order by item.name asc")
    List<OrderItem> searchAllDistinctByName(String search);

    List<OrderItem> findAllByName(String name);
}
