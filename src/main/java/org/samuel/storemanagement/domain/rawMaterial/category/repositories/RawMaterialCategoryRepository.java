package org.samuel.storemanagement.domain.rawMaterial.category.repositories;

import org.samuel.storemanagement.domain.rawMaterial.category.models.RawMaterialCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RawMaterialCategoryRepository extends JpaRepository<RawMaterialCategory, Long>, JpaSpecificationExecutor<RawMaterialCategory> {
}
