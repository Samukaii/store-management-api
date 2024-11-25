package org.samuel.storemanagement.domain.rawMaterial.category.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.samuel.storemanagement.domain.product.product.exceptions.ProductNotFoundException;
import org.samuel.storemanagement.domain.rawMaterial.category.dtos.RawMaterialCategoryCreate;
import org.samuel.storemanagement.domain.rawMaterial.category.exceptions.RawMaterialCategoryNotFoundException;
import org.samuel.storemanagement.domain.rawMaterial.category.mappers.RawMaterialCategoryMapper;
import org.samuel.storemanagement.domain.rawMaterial.category.models.RawMaterialCategory;
import org.samuel.storemanagement.domain.rawMaterial.category.repositories.RawMaterialCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RawMaterialCategoryService {
    private final RawMaterialCategoryRepository repository;
    private final RawMaterialCategoryMapper mapper;

    @SneakyThrows
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

    public List<RawMaterialCategory> autocomplete(Optional<String> search) {
        return search.map(repository::searchAllByText).orElseGet(this::findAll);
    }

    @SneakyThrows
    public RawMaterialCategory updateById(Long id, RawMaterialCategoryCreate payload) throws ProductNotFoundException {
        RawMaterialCategory category = findById(id);

        mapper.update(payload, category);

        return repository.save(category);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
