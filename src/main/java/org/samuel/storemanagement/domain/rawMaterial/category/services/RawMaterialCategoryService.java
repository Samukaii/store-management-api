package org.samuel.storemanagement.domain.rawMaterial.category.services;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.product.product.exceptions.ProductNotFoundException;
import org.samuel.storemanagement.domain.rawMaterial.category.dtos.RawMaterialCategoryCreate;
import org.samuel.storemanagement.domain.rawMaterial.category.dtos.RawMaterialCategoryUpdate;
import org.samuel.storemanagement.domain.rawMaterial.category.exceptions.RawMaterialCategoryNotFoundException;
import org.samuel.storemanagement.domain.rawMaterial.category.mappers.RawMaterialCategoryMapper;
import org.samuel.storemanagement.domain.rawMaterial.category.models.RawMaterialCategory;
import org.samuel.storemanagement.domain.rawMaterial.category.repositories.RawMaterialCategoryRepository;
import org.samuel.storemanagement.general.filters.FilterSpecificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RawMaterialCategoryService {
    private final RawMaterialCategoryRepository repository;
    private final RawMaterialCategoryMapper mapper;
    private final FilterSpecificationService<RawMaterialCategory> specificationService;

    public RawMaterialCategory create(RawMaterialCategoryCreate payload) {
        RawMaterialCategory result = mapper.toEntity(payload);

        return repository.save(result);
    }

    public RawMaterialCategory findById(Long id) throws RawMaterialCategoryNotFoundException {
        return repository.findById(id).orElseThrow(RawMaterialCategoryNotFoundException::new);
    }

    public List<RawMaterialCategory> findAll() {
        return repository.findAll();
    }

    public List<RawMaterialCategory> autocomplete(Map<String, String> filters) {
        var specification = specificationService.buildSpecification(filters);

        return repository.findAll(specification);
    }

    public RawMaterialCategory updateById(Long id, RawMaterialCategoryUpdate payload) throws ProductNotFoundException, RawMaterialCategoryNotFoundException {
        RawMaterialCategory category = findById(id);

        mapper.update(payload, category);

        return repository.save(category);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
