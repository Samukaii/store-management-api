package org.samuel.storemanagement.domain.product.ingredient.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.samuel.storemanagement.domain.product.ingredient.dtos.ProductIngredientCreate;
import org.samuel.storemanagement.domain.product.ingredient.dtos.ProductIngredientResponse;
import org.samuel.storemanagement.domain.product.ingredient.mappers.ProductIngredientMapper;
import org.samuel.storemanagement.domain.product.ingredient.services.ProductIngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("products/{productId}/ingredients")
public class ProductIngredientController {
    private final ProductIngredientService service;
    private final ProductIngredientMapper mapper;

    @PostMapping
    @SneakyThrows
    public ResponseEntity<ProductIngredientResponse> create(@PathVariable Long productId, @RequestBody ProductIngredientCreate payload) {
        return ResponseEntity.ok().body(mapper.toDto(service.create(productId, payload)));
    }

    @GetMapping
    @SneakyThrows
    public ResponseEntity<List<ProductIngredientResponse>> getAll(@PathVariable Long productId) {
        return ResponseEntity.ok().body(mapper.toListDto(service.findAll(productId)));
    }

    @GetMapping("/{id}")
    @SneakyThrows
    public ResponseEntity<ProductIngredientResponse> getById(@PathVariable Long productId, @PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.toDto(service.findById(productId, id)));
    }

    @PutMapping("/{id}")
    @SneakyThrows
    public ResponseEntity<ProductIngredientResponse> updateById(@PathVariable Long productId, @PathVariable Long id, @RequestBody ProductIngredientCreate payload) {
        return ResponseEntity.ok().body(mapper.toDto(service.updateById(productId, id, payload)));
    }

    @Transactional
    @DeleteMapping("/{id}")
    @SneakyThrows
    public ResponseEntity<String> deleteById(@PathVariable Long productId, @PathVariable Long id) {
        service.deleteById(id, productId);

        return ResponseEntity.noContent().build();
    }
}
