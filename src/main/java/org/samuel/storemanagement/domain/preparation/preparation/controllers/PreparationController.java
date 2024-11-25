package org.samuel.storemanagement.domain.preparation.preparation.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.samuel.storemanagement.domain.preparation.preparation.dtos.PreparationCreate;
import org.samuel.storemanagement.domain.preparation.preparation.dtos.PreparationUpdate;
import org.samuel.storemanagement.domain.preparation.preparation.dtos.PreparationViewResponse;
import org.samuel.storemanagement.domain.preparation.preparation.mappers.PreparationMapper;
import org.samuel.storemanagement.domain.preparation.preparation.services.PreparationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("preparations")
public class PreparationController {
    private final PreparationService service;
    private final PreparationMapper mapper;

    @PostMapping
    public ResponseEntity<PreparationViewResponse> create(@RequestBody PreparationCreate payload) {
        return ResponseEntity.ok().body(mapper.toDto(service.create(payload)));
    }

    @GetMapping
    @SneakyThrows
    public ResponseEntity<List<PreparationViewResponse>> getAll() {
        return ResponseEntity.ok().body(mapper.toListDto(service.findAll()));
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<List<PreparationViewResponse>> autocomplete(@RequestParam Optional<String> search) {
        return ResponseEntity.ok(mapper.toListDto(service.autocomplete(search)));
    }

    @GetMapping("/{id}")
    @SneakyThrows
    public ResponseEntity<PreparationViewResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.mapper.toDto(service.findById(id)));
    }

    @PutMapping("/{id}")
    @SneakyThrows
    public ResponseEntity<PreparationViewResponse> update(@PathVariable Long id, @RequestBody PreparationUpdate payload) {
        service.updateById(id, payload);

        return ResponseEntity.ok().body(this.mapper.toDto(service.findById(id)));
    }

    @Transactional
    @DeleteMapping("/{id}")
    @SneakyThrows
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
