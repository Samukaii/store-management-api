package org.samuel.storemanagement.domain.product.category.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.samuel.storemanagement.domain.product.category.dtos.ProductCategoryCreate;
import org.samuel.storemanagement.domain.product.category.dtos.ProductCategoryViewResponse;
import org.samuel.storemanagement.domain.product.category.mappers.ProductCategoryMapper;
import org.samuel.storemanagement.domain.product.category.services.ProductCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("products/categories")
public class ProductCategoryController {
    private final ProductCategoryService service;
    private final ProductCategoryMapper mapper;

    @PostMapping
    @SneakyThrows
    public ResponseEntity<ProductCategoryViewResponse> create(@RequestBody ProductCategoryCreate payload) {
        return ResponseEntity.ok().body(mapper.toDto(service.create(payload)));
    }

    @GetMapping("/autocomplete")
    @SneakyThrows
    public ResponseEntity<List<ProductCategoryViewResponse>> autocomplete(@RequestParam Optional<String> search) {
        return ResponseEntity.ok().body(mapper.toListDto(service.autocomplete(search)));
    }

    @GetMapping
    public ResponseEntity<List<ProductCategoryViewResponse>> getAll() {
        return ResponseEntity.ok().body(mapper.toListDto(service.findAll()));
    }

    @GetMapping("/{id}")
    @SneakyThrows
    public ResponseEntity<ProductCategoryViewResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.toDto(service.findById(id)));
    }

    @PutMapping("/{id}")
    @SneakyThrows
    public ResponseEntity<ProductCategoryViewResponse> getById(@PathVariable Long id, @RequestBody ProductCategoryCreate product) {
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
