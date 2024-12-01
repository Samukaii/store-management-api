package org.samuel.storemanagement.domain.order.item.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.samuel.storemanagement.domain.analytics.dtos.OrderItemSelling;
import org.samuel.storemanagement.domain.order.item.models.OrderItem;
import org.samuel.storemanagement.domain.order.order.models.Order;
import org.samuel.storemanagement.domain.product.product.models.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class OrderItemRepositoryCustomImpl implements OrderItemRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrderItemSelling> getBestSelling(Specification<OrderItem> specification) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderItemSelling> query = cb.createQuery(OrderItemSelling.class);
        Root<OrderItem> root = query.from(OrderItem.class);

        Join<OrderItem, Product> productJoin = root.join("product");
        Join<OrderItem, Order> orderJoin = root.join("order");

        Predicate predicate = cb.conjunction();

        query.where(cb.and(predicate, specification.toPredicate(root, query, cb)));

        query.select(cb.construct(
                OrderItemSelling.class,
                root.get("quantity"),
                root.get("total"),
                productJoin.get("profitMargin"),
                productJoin.get("name"),
                orderJoin.get("date")
        ));

        return entityManager.createQuery(query).getResultList();
    }
}
