package org.samuel.storemanagement.domain.product.category.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.samuel.storemanagement.domain.product.category.dtos.ProductCategoryCreate;
import org.samuel.storemanagement.domain.product.category.exceptions.ProductCategoryNotFoundException;
import org.samuel.storemanagement.domain.product.category.mappers.ProductCategoryMapper;
import org.samuel.storemanagement.domain.product.category.models.ProductCategory;
import org.samuel.storemanagement.domain.product.category.repositories.ProductCategoryRepository;
import org.samuel.storemanagement.domain.product.product.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryRepository repository;
    private final ProductCategoryMapper mapper;

    @SneakyThrows
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

    public List<ProductCategory> autocomplete(Optional<String> search) {
        return search.map(repository::searchAllByText).orElseGet(this::findAll);
    }

    @SneakyThrows
    public ProductCategory updateById(Long id, ProductCategoryCreate payload) throws ProductNotFoundException {
        ProductCategory category = findById(id);

        mapper.update(payload, category);

        return repository.save(category);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
