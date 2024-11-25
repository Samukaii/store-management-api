package org.samuel.storemanagement.domain.preparation.ingredient.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.samuel.storemanagement.domain.preparation.ingredient.dtos.PreparationIngredientCreate;
import org.samuel.storemanagement.domain.preparation.ingredient.dtos.PreparationIngredientViewResponse;
import org.samuel.storemanagement.domain.preparation.ingredient.mappers.PreparationIngredientMapper;
import org.samuel.storemanagement.domain.preparation.ingredient.services.PreparationIngredientService;
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
    @SneakyThrows
    public ResponseEntity<PreparationIngredientViewResponse> create(@PathVariable Long preparationId, @RequestBody PreparationIngredientCreate payload) {
        return ResponseEntity.ok().body(mapper.toDto(service.create(preparationId, payload)));
    }

    @GetMapping
    @SneakyThrows
    public ResponseEntity<List<PreparationIngredientViewResponse>> getAll(@PathVariable Long preparationId) {
        return ResponseEntity.ok().body(mapper.toListDto(service.findAll(preparationId)));
    }

    @GetMapping("/{id}")
    @SneakyThrows
    public ResponseEntity<PreparationIngredientViewResponse> getById(@PathVariable Long preparationId, @PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.toDto(service.findById(preparationId, id)));
    }

    @PutMapping("/{id}")
    @SneakyThrows
    public ResponseEntity<PreparationIngredientViewResponse> updateById(@PathVariable Long preparationId, @PathVariable Long id, @RequestBody PreparationIngredientCreate payload) {
        return ResponseEntity.ok().body(mapper.toDto(service.updateById(preparationId, id, payload)));
    }

    @Transactional
    @DeleteMapping("/{id}")
    @SneakyThrows
    public ResponseEntity<String> deleteById(@PathVariable Long preparationId, @PathVariable Long id) {
        service.deleteById(id, preparationId);

        return ResponseEntity.noContent().build();
    }
}
