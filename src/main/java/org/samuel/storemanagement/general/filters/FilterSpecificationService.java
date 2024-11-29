package org.samuel.storemanagement.general.filters;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@Service
public class FilterSpecificationService<T> {
    private final Map<String, FilterPredicate<T>> operators = new HashMap<>();

    public FilterSpecificationService() {
        registerDefaultOperators();
    }

    public Specification<T> buildSpecification(Map<String, String> filters) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();
            String sortProperty = filters.get("sortProperty");
            String sortDirection = filters.get("sortDirection");

            for (Map.Entry<String, String> entry : filters.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                String[] keyParts = key.split(":");
                String property = keyParts[0];
                String operator = keyParts.length > 1 ? keyParts[1] : ":isNull";

                FilterPredicate<T> filterPredicate = operators.get(operator);

                if (filterPredicate != null) {
                    predicate = builder.and(predicate, filterPredicate.apply(root, builder, property, value));
                }
            }

            if (sortProperty != null && !sortProperty.isEmpty()) {
                Path<Object> sortPath = resolvePath(root, sortProperty);

                if (query != null)
                    query.orderBy("desc".equalsIgnoreCase(sortDirection)
                            ? builder.desc(sortPath)
                            : builder.asc(sortPath));
            }


            return predicate;
        };
    }

    @SuppressWarnings("unchecked")
    private <Y> Path<Y> resolvePath(Root<T> root, String property) {
        Path<Y> path = (Path<Y>) root;
        for (String part : property.split("\\.")) {
            path = path.get(part);
        }
        return path;
    }

    private void registerDefaultOperators() {
        operators.put("min", (root, builder, key, value) -> {
            try {
                LocalDateTime minDate = LocalDateTime.parse(value);
                return builder.greaterThanOrEqualTo(resolvePath(root, key), minDate);
            } catch (DateTimeParseException e) {
                double minValue = Double.parseDouble(value);
                return builder.greaterThanOrEqualTo(resolvePath(root, key), minValue);
            }
        });

        operators.put("max", (root, builder, key, value) -> {
            try {
                LocalDateTime maxDate = LocalDateTime.parse(value);
                return builder.lessThanOrEqualTo(resolvePath(root, key), maxDate);
            } catch (DateTimeParseException e) {
                double maxValue = Double.parseDouble(value);
                return builder.lessThanOrEqualTo(resolvePath(root, key), maxValue);
            }
        });

        operators.put("equal", (root, builder, key, value) -> {
            Object convertedValue = Double.valueOf(value);
            return builder.equal(resolvePath(root, key), convertedValue);
        });

        operators.put("search", (root, builder, key, value) ->
                builder.like(builder.lower(resolvePath(root, key).as(String.class)), "%" + value.toLowerCase() + "%")
        );

        operators.put("hasAssociation", (root, builder, key, value) -> {
            boolean hasAssociation = Boolean.parseBoolean(value);
            if (hasAssociation) {
                return builder.isNotEmpty(resolvePath(root, key));
            } else {
                return builder.isEmpty(resolvePath(root, key));
            }
        });

        operators.put("isNull", (root, builder, key, value) -> builder.isNull(resolvePath(root, key)));
    }
}
