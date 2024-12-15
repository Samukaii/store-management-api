package org.samuel.storemanagement.domain.preparation.ingredient.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.preparation.ingredient.dtos.PreparationIngredientCreate;
import org.samuel.storemanagement.domain.preparation.ingredient.dtos.PreparationIngredientViewResponse;
import org.samuel.storemanagement.domain.preparation.ingredient.exceptions.PreparationIngredientNotFoundException;
import org.samuel.storemanagement.domain.preparation.ingredient.mappers.PreparationIngredientMapper;
import org.samuel.storemanagement.domain.preparation.ingredient.services.PreparationIngredientService;
import org.samuel.storemanagement.domain.preparation.preparation.exceptions.PreparationNotFoundException;
import org.samuel.storemanagement.domain.product.product.exceptions.ProductNotFoundException;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.exceptions.RawMaterialNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("preparations/{preparationId}/ingredients")
public class PreparationIngredientController {
    private final PreparationIngredientService service;
    private final PreparationIngredientMapper mapper;

    @PostMapping
    public ResponseEntity<PreparationIngredientViewResponse> create(@PathVariable Long preparationId, @RequestBody @Valid PreparationIngredientCreate payload) throws PreparationNotFoundException, RawMaterialNotFoundException {
        return ResponseEntity.ok().body(mapper.toDto(service.create(preparationId, payload)));
    }

    @GetMapping
    public ResponseEntity<List<PreparationIngredientViewResponse>> getAll(@PathVariable Long preparationId) {
        return ResponseEntity.ok().body(mapper.toListDto(service.findAll(preparationId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreparationIngredientViewResponse> getById(@PathVariable Long preparationId, @PathVariable Long id) throws PreparationIngredientNotFoundException {
        return ResponseEntity.ok().body(mapper.toDto(service.findById(preparationId, id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PreparationIngredientViewResponse> updateById(@PathVariable Long preparationId, @PathVariable Long id, @RequestBody @Valid PreparationIngredientCreate payload) throws RawMaterialNotFoundException, ProductNotFoundException {
        return ResponseEntity.ok().body(mapper.toDto(service.updateById(preparationId, id, payload)));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long preparationId, @PathVariable Long id) throws PreparationIngredientNotFoundException {
        service.deleteById(id, preparationId);

        return ResponseEntity.noContent().build();
    }
}
