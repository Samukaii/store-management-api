package org.samuel.storemanagement.domain.rawMaterial.category.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.samuel.storemanagement.domain.rawMaterial.category.dtos.RawMaterialCategoryCreate;
import org.samuel.storemanagement.domain.rawMaterial.category.dtos.RawMaterialCategoryViewResponse;
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
    @SneakyThrows
    public ResponseEntity<RawMaterialCategoryViewResponse> create(@RequestBody RawMaterialCategoryCreate payload) {
        return ResponseEntity.ok().body(mapper.toDto(service.create(payload)));
    }

    @GetMapping("/autocomplete")
    @SneakyThrows
    public ResponseEntity<List<RawMaterialCategoryViewResponse>> autocomplete(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok().body(mapper.toListDto(service.autocomplete(filters)));
    }

    @GetMapping
    public ResponseEntity<List<RawMaterialCategoryViewResponse>> getAll() {
        return ResponseEntity.ok().body(mapper.toListDto(service.findAll()));
    }

    @GetMapping("/{id}")
    @SneakyThrows
    public ResponseEntity<RawMaterialCategoryViewResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.toDto(service.findById(id)));
    }

    @PutMapping("/{id}")
    @SneakyThrows
    public ResponseEntity<RawMaterialCategoryViewResponse> getById(@PathVariable Long id, @RequestBody RawMaterialCategoryCreate product) {
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
