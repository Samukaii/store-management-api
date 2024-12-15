package org.samuel.storemanagement.domain.preparation.preparation.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.preparation.preparation.dtos.PreparationCreate;
import org.samuel.storemanagement.domain.preparation.preparation.dtos.PreparationUpdate;
import org.samuel.storemanagement.domain.preparation.preparation.dtos.PreparationViewResponse;
import org.samuel.storemanagement.domain.preparation.preparation.exceptions.PreparationNotFoundException;
import org.samuel.storemanagement.domain.preparation.preparation.mappers.PreparationMapper;
import org.samuel.storemanagement.domain.preparation.preparation.services.PreparationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("preparations")
public class PreparationController {
    private final PreparationService service;
    private final PreparationMapper mapper;

    @PostMapping
    public ResponseEntity<PreparationViewResponse> create(@RequestBody @Valid PreparationCreate payload) {
        return ResponseEntity.ok().body(mapper.toDto(service.create(payload)));
    }

    @GetMapping
    public ResponseEntity<List<PreparationViewResponse>> getAll() {
        return ResponseEntity.ok().body(mapper.toListDto(service.findAll()));
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<List<PreparationViewResponse>> autocomplete(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok(mapper.toListDto(service.autocomplete(filters)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreparationViewResponse> getOne(@PathVariable Long id) throws PreparationNotFoundException {
        return ResponseEntity.ok().body(this.mapper.toDto(service.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PreparationViewResponse> update(@PathVariable Long id, @RequestBody @Valid PreparationUpdate payload) throws PreparationNotFoundException {
        service.updateById(id, payload);

        return ResponseEntity.ok().body(this.mapper.toDto(service.findById(id)));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
