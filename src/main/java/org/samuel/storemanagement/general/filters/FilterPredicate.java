package org.samuel.storemanagement.general.filters;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@FunctionalInterface
public interface FilterPredicate<T> {
    Predicate apply(Root<T> root, CriteriaBuilder builder, String key, String value);
}
