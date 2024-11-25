package org.samuel.storemanagement.general.filters;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FilterSpecificationService<T> {
    public Specification<T> buildSpecification(Map<String, String> filters) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();
            String sortProperty = null;
            String sortDirection = null;

            for (Map.Entry<String, String> entry : filters.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if ("sortProperty".equals(key)) {
                    sortProperty = value;
                } else if ("sortDirection".equals(key)) {
                    sortDirection = value;
                } else if (key.contains(".")) {
                    String[] parts = key.split("\\.");
                    Path<Object> path = root.get(parts[0]);

                    for (int i = 1; i < parts.length; i++) {
                        path = path.get(parts[i]);
                    }

                    predicate = builder.and(predicate, builder.equal(path, value));
                } else if (key.endsWith("Min")) {
                    predicate = builder.and(predicate,
                            builder.greaterThanOrEqualTo(root.get(key.replace("Min", "")), value));
                } else if (key.endsWith("Max")) {
                    predicate = builder.and(predicate,
                            builder.lessThanOrEqualTo(root.get(key.replace("Max", "")), value));
                }
                else if (key.endsWith("Name")) {
                    predicate = builder.and(predicate,
                            builder.like(root.get(key), "%" + value + "%"));
                }
                else {
                    predicate = builder.and(predicate, builder.isNull(root.get(key)));
                }
            }

            if (sortProperty != null && !sortProperty.isEmpty()) {
                Path<Object> sortPath = root.get(sortProperty);

                if(query == null)
                    return predicate;

                query.orderBy("desc".equalsIgnoreCase(sortDirection)
                        ? builder.desc(sortPath)
                        : builder.asc(sortPath));
            }

            return predicate;
        };
    }
}
