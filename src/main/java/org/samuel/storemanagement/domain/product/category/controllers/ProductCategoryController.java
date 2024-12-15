package org.samuel.storemanagement.domain.product.category.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.product.category.dtos.ProductCategoryCreate;
import org.samuel.storemanagement.domain.product.category.dtos.ProductCategoryUpdate;
import org.samuel.storemanagement.domain.product.category.dtos.ProductCategoryViewResponse;
import org.samuel.storemanagement.domain.product.category.exceptions.ProductCategoryNotFoundException;
import org.samuel.storemanagement.domain.product.category.mappers.ProductCategoryMapper;
import org.samuel.storemanagement.domain.product.category.services.ProductCategoryService;
import org.samuel.storemanagement.domain.product.product.exceptions.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("products/categories")
public class ProductCategoryController {
    private final ProductCategoryService service;
    private final ProductCategoryMapper mapper;

    @PostMapping
    public ResponseEntity<ProductCategoryViewResponse> create(@RequestBody @Valid ProductCategoryCreate payload) {
        return ResponseEntity.ok().body(mapper.toDto(service.create(payload)));
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<List<ProductCategoryViewResponse>> autocomplete(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok().body(mapper.toListDto(service.autocomplete(filters)));
    }

    @GetMapping
    public ResponseEntity<List<ProductCategoryViewResponse>> getAll() {
        return ResponseEntity.ok().body(mapper.toListDto(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryViewResponse> getById(@PathVariable Long id) throws ProductCategoryNotFoundException {
        return ResponseEntity.ok().body(mapper.toDto(service.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryViewResponse> getById(@PathVariable Long id, @RequestBody @Valid ProductCategoryUpdate product) throws ProductCategoryNotFoundException, ProductNotFoundException {
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
