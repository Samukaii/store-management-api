package org.samuel.storemanagement.domain.rawMaterial.category.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.product.product.exceptions.ProductNotFoundException;
import org.samuel.storemanagement.domain.rawMaterial.category.dtos.RawMaterialCategoryCreate;
import org.samuel.storemanagement.domain.rawMaterial.category.dtos.RawMaterialCategoryUpdate;
import org.samuel.storemanagement.domain.rawMaterial.category.dtos.RawMaterialCategoryViewResponse;
import org.samuel.storemanagement.domain.rawMaterial.category.exceptions.RawMaterialCategoryNotFoundException;
import org.samuel.storemanagement.domain.rawMaterial.category.mappers.RawMaterialCategoryMapper;
import org.samuel.storemanagement.domain.rawMaterial.category.services.RawMaterialCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("raw_materials/categories")
public class RawMaterialCategoryController {
    private final RawMaterialCategoryService service;
    private final RawMaterialCategoryMapper mapper;

    @PostMapping
    public ResponseEntity<RawMaterialCategoryViewResponse> create(@RequestBody @Valid RawMaterialCategoryCreate payload) {
        return ResponseEntity.ok().body(mapper.toDto(service.create(payload)));
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<List<RawMaterialCategoryViewResponse>> autocomplete(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok().body(mapper.toListDto(service.autocomplete(filters)));
    }

    @GetMapping
    public ResponseEntity<List<RawMaterialCategoryViewResponse>> getAll() {
        return ResponseEntity.ok().body(mapper.toListDto(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RawMaterialCategoryViewResponse> getById(@PathVariable Long id) throws RawMaterialCategoryNotFoundException {
        return ResponseEntity.ok().body(mapper.toDto(service.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RawMaterialCategoryViewResponse> updateById(@PathVariable Long id, @RequestBody @Valid RawMaterialCategoryUpdate product) throws RawMaterialCategoryNotFoundException, ProductNotFoundException {
        service.updateById(id, product);

        return ResponseEntity.ok().body(mapper.toDto(service.findById(id)));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
