package org.samuel.storemanagement.domain.rawMaterial.rawMaterial.repositories;

import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.models.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long>, JpaSpecificationExecutor<RawMaterial> {
    @Query(value = "select material from RawMaterial material where " +
            "(lower(material.name)) like %?1% order by material.name asc")
    List<RawMaterial> searchAllByText(String search);
}
