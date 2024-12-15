package org.samuel.storemanagement.domain.rawMaterial.rawMaterial.services;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.rawMaterial.category.exceptions.RawMaterialCategoryNotFoundException;
import org.samuel.storemanagement.domain.rawMaterial.category.models.RawMaterialCategory;
import org.samuel.storemanagement.domain.rawMaterial.category.services.RawMaterialCategoryService;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.dtos.RawMaterialCreate;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.dtos.RawMaterialUpdate;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.events.RawMaterialPublisher;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.exceptions.RawMaterialNotFoundException;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.mappers.RawMaterialMapper;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.models.RawMaterial;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.repositories.RawMaterialRepository;
import org.samuel.storemanagement.general.filters.FilterSpecificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RawMaterialService {
    private final RawMaterialRepository repository;
    private final RawMaterialPublisher publisher;
    private final RawMaterialMapper mapper;
    private final RawMaterialCategoryService categoryService;
    private final FilterSpecificationService<RawMaterial> filterSpecificationService;

    public RawMaterial create(RawMaterialCreate payload) throws RawMaterialCategoryNotFoundException {
        RawMaterial material = mapper.toModel(payload);

        if(payload.getCategoryId() != null) {
            RawMaterialCategory category = categoryService.findById(payload.getCategoryId());

            material.setCategory(category);
        }

        return save(material);
    }

    public List<RawMaterial> autocomplete(Map<String, String> filters) {
        var specification = filterSpecificationService.buildSpecification(filters);

        return repository.findAll(specification);
    }

    public RawMaterial findById(Long id) throws RawMaterialNotFoundException {
        Optional<RawMaterial> foundFoodInput = repository.findById(id);

        return foundFoodInput.orElseThrow(RawMaterialNotFoundException::new);
    }

    public RawMaterial updateById(Long id, RawMaterialUpdate payload) throws RawMaterialNotFoundException, RawMaterialCategoryNotFoundException {
        RawMaterial material = repository.findById(id).orElseThrow(RawMaterialNotFoundException::new);

        mapper.update(payload, material);

        if(payload.getCategoryId() != null) {
            RawMaterialCategory category = categoryService.findById(payload.getCategoryId());

            material.setCategory(category);
        }

        return save(material);
    }

    public void deleteById(Long id) throws RawMaterialNotFoundException {
        delete(id);
    }

    public List<RawMaterial> findAll(Map<String, String> filters) {
        var specification = filterSpecificationService.buildSpecification(filters);

        return repository.findAll(specification);
    }

    public List<RawMaterial> findAll() {
        return repository.findAll();
    }

    private void updateCostPerUnit(RawMaterial foodInput) {
        if (foodInput.getCost() == null) return;

        Double costPerUnit = foodInput.getCost() / foodInput.getQuantity();

        foodInput.setCostPerUnit(costPerUnit);
    }

    private RawMaterial save(RawMaterial foodInput) {
        updateCostPerUnit(foodInput);

        RawMaterial result = repository.save(foodInput);

        publisher.emitChanges(result);

        return result;
    }

    public void delete(Long id) throws RawMaterialNotFoundException {
        RawMaterial result = findById(id);

        publisher.emitDelete(result);

        repository.deleteById(id);
    }
}
