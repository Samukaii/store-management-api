package org.samuel.storemanagement.domain.rawMaterial.rawMaterial.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.rawMaterial.category.exceptions.RawMaterialCategoryNotFoundException;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.dtos.RawMaterialCreate;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.dtos.RawMaterialUpdate;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.dtos.RawMaterialViewResponse;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.exceptions.RawMaterialNotFoundException;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.mappers.RawMaterialMapper;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.services.RawMaterialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("raw_materials")
public class RawMaterialController {
    private final RawMaterialService service;
    private final RawMaterialMapper mapper;

    @PostMapping
    public ResponseEntity<RawMaterialViewResponse> create(@RequestBody @Valid RawMaterialCreate product) throws RawMaterialCategoryNotFoundException {
        return ResponseEntity.ok().body(mapper.toDto(service.create(product)));
    }

    @GetMapping
    public ResponseEntity<List<RawMaterialViewResponse>> getAll(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok().body(mapper.toListDto(service.findAll(filters)));
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<List<RawMaterialViewResponse>> autocomplete(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok(mapper.toListDto(service.autocomplete(filters)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RawMaterialViewResponse> getById(@PathVariable Long id) throws RawMaterialNotFoundException {
        return ResponseEntity.ok().body(this.mapper.toDto(service.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RawMaterialViewResponse> updateById(@PathVariable Long id, @RequestBody @Valid RawMaterialUpdate payload) throws RawMaterialNotFoundException, RawMaterialCategoryNotFoundException {
        return ResponseEntity.ok().body(this.mapper.toDto(service.updateById(id, payload)));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) throws RawMaterialNotFoundException {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
