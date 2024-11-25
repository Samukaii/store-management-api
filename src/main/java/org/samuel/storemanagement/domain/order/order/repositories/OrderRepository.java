package org.samuel.storemanagement.domain.order.order.repositories;

import org.samuel.storemanagement.domain.order.order.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderFiltersRepository {
    List<Order> findAllByOrderByDateAsc();
    Optional<Order> findByCode(String code);
}
