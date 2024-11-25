package org.samuel.storemanagement.domain.preparation.preparation.repositories;

import org.samuel.storemanagement.domain.preparation.preparation.models.Preparation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PreparationRepository extends JpaRepository<Preparation, Long> {
    @Query(value = "select preparation from Preparation preparation where " +
            "(lower(preparation.name)) like %?1% order by preparation.name asc")
    List<Preparation> searchAllByText(String search);
}
