package org.samuel.storemanagement.domain.preparation.preparation.repositories;

import org.samuel.storemanagement.domain.preparation.preparation.models.Preparation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PreparationRepository extends JpaRepository<Preparation, Long>, JpaSpecificationExecutor<Preparation> {
}
