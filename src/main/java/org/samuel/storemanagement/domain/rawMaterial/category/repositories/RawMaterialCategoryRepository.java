package org.samuel.storemanagement.domain.rawMaterial.category.repositories;

import org.samuel.storemanagement.domain.rawMaterial.category.models.RawMaterialCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RawMaterialCategoryRepository extends JpaRepository<RawMaterialCategory, Long> {
    @Query(value = "select category from RawMaterialCategory category where " +
            "(lower(category.name)) like %?1% order by category.name asc")
    List<RawMaterialCategory> searchAllByText(String search);}
