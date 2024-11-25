package org.samuel.storemanagement.domain.product.category.repositories;

import org.samuel.storemanagement.domain.product.category.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    @Query(value = "select category from ProductCategory category where " +
            "(lower(category.name)) like %?1% order by category.name asc")
    List<ProductCategory> searchAllByText(String search);}
