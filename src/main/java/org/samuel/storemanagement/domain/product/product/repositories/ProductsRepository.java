package org.samuel.storemanagement.domain.product.product.repositories;

import org.samuel.storemanagement.domain.product.product.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByIntegrationName(String integrationName);
}
