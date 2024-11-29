package org.samuel.storemanagement.domain.rawMaterial.rawMaterial.repositories;

import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.models.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long>, JpaSpecificationExecutor<RawMaterial> {
}
