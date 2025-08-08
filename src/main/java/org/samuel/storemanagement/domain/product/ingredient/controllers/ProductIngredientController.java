package org.samuel.storemanagement.domain.product.ingredient.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.preparation.preparation.exceptions.PreparationNotFoundException;
import org.samuel.storemanagement.domain.product.ingredient.dtos.ProductIngredientCreate;
import org.samuel.storemanagement.domain.product.ingredient.dtos.ProductIngredientResponse;
import org.samuel.storemanagement.domain.product.ingredient.exceptions.ProductImportIngredientEmpty;
import org.samuel.storemanagement.domain.product.ingredient.exceptions.ProductIngredientNotFoundException;
import org.samuel.storemanagement.domain.product.ingredient.mappers.ProductIngredientMapper;
import org.samuel.storemanagement.domain.product.ingredient.services.ProductIngredientService;
import org.samuel.storemanagement.domain.product.product.dtos.ProductImportIngredients;
import org.samuel.storemanagement.domain.product.product.dtos.ProductResponse;
import org.samuel.storemanagement.domain.product.product.exceptions.ProductNotFoundException;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.exceptions.RawMaterialNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("products/{productId}/ingredients")
public class ProductIngredientController {
    private final ProductIngredientService service;
    private final ProductIngredientMapper mapper;

    @PostMapping
    public ResponseEntity<ProductIngredientResponse> create(@PathVariable Long productId, @RequestBody @Valid ProductIngredientCreate payload) throws PreparationNotFoundException, RawMaterialNotFoundException {
        return ResponseEntity.ok().body(mapper.toDto(service.create(productId, payload)));
    }

    @GetMapping
    public ResponseEntity<List<ProductIngredientResponse>> getAll(@PathVariable Long productId, @RequestParam Map<String, String> params) {
        return ResponseEntity.ok().body(service.findAllCalculated(productId, params));
    }

    @PostMapping("/import")
    public ResponseEntity<ProductResponse> importIngredients(@PathVariable Long productId, @RequestBody @Valid ProductImportIngredients payload) throws ProductImportIngredientEmpty {
        service.importIngredients(productId, payload);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductIngredientResponse> getById(@PathVariable Long productId, @PathVariable Long id) throws ProductIngredientNotFoundException {
        return ResponseEntity.ok().body(mapper.toDto(service.findById(productId, id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductIngredientResponse> updateById(@PathVariable Long productId, @PathVariable Long id, @RequestBody @Valid ProductIngredientCreate payload) throws PreparationNotFoundException, ProductNotFoundException, RawMaterialNotFoundException {
        return ResponseEntity.ok().body(mapper.toDto(service.updateById(productId, id, payload)));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long productId, @PathVariable Long id) throws ProductIngredientNotFoundException {
        service.deleteById(id, productId);

        return ResponseEntity.noContent().build();
    }
}
