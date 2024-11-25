package org.samuel.storemanagement.domain.order.order.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.order.order.dtos.OrderFiltersParams;
import org.samuel.storemanagement.domain.order.order.models.Order;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class OrderFiltersRepositoryImpl implements OrderFiltersRepository {
    private final EntityManager entityManager;

    @Override
    public List<Order> findAllWithFilters(OrderFiltersParams filtersParams) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);

        Root<Order> root = query.from(Order.class);

        Path<ZonedDateTime> orderDate = root.get("date");

        if (filtersParams.getStartDate() != null && filtersParams.getEndDate() != null) {
            Predicate period = builder.between(orderDate, filtersParams.getStartDate(), filtersParams.getEndDate());
            query.where(period);
        }

        String sortProperty = filtersParams.getSortProperty() == null ? "date" : filtersParams.getSortProperty();
        String sortDirection = filtersParams.getSortDirection() == null ? "desc" : filtersParams.getSortDirection();

        if (Objects.equals(sortDirection, "asc")) query.orderBy(builder.asc(root.get(sortProperty)));
        else query.orderBy(builder.desc(root.get(sortProperty)));

        return entityManager.createQuery(query).getResultList();
    }
}
