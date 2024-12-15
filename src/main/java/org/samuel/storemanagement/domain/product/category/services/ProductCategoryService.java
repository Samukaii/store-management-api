package org.samuel.storemanagement.domain.product.category.services;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.product.category.dtos.ProductCategoryCreate;
import org.samuel.storemanagement.domain.product.category.dtos.ProductCategoryUpdate;
import org.samuel.storemanagement.domain.product.category.exceptions.ProductCategoryNotFoundException;
import org.samuel.storemanagement.domain.product.category.mappers.ProductCategoryMapper;
import org.samuel.storemanagement.domain.product.category.models.ProductCategory;
import org.samuel.storemanagement.domain.product.category.repositories.ProductCategoryRepository;
import org.samuel.storemanagement.domain.product.product.exceptions.ProductNotFoundException;
import org.samuel.storemanagement.general.filters.FilterSpecificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryRepository repository;
    private final ProductCategoryMapper mapper;
    private final FilterSpecificationService<ProductCategory> specificationService;

    public ProductCategory create(ProductCategoryCreate payload) {
        ProductCategory result = mapper.toEntity(payload);

        return repository.save(result);
    }

    public ProductCategory findById(Long id) throws ProductCategoryNotFoundException {
        return repository.findById(id).orElseThrow(ProductCategoryNotFoundException::new);
    }

    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    public List<ProductCategory> autocomplete(Map<String, String> filters) {
        var specification = specificationService.buildSpecification(filters);

        return repository.findAll(specification);
    }

    public ProductCategory updateById(Long id, ProductCategoryUpdate payload) throws ProductNotFoundException, ProductCategoryNotFoundException {
        ProductCategory category = findById(id);

        mapper.update(payload, category);

        return repository.save(category);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
