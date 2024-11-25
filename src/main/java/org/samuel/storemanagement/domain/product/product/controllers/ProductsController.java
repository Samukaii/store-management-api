package org.samuel.storemanagement.domain.product.product.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.samuel.storemanagement.domain.product.product.dtos.ProductResponse;
import org.samuel.storemanagement.domain.product.product.dtos.ProductUpdate;
import org.samuel.storemanagement.domain.product.product.exceptions.ProductFieldNotReceivedException;
import org.samuel.storemanagement.domain.product.product.exceptions.ProductNotFoundException;
import org.samuel.storemanagement.domain.product.product.mappers.ProductsMapper;
import org.samuel.storemanagement.domain.product.product.models.Product;
import org.samuel.storemanagement.domain.product.product.services.ProductsService;
import org.samuel.storemanagement.general.dto.FilePayload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("products")
public class ProductsController {
    private final ProductsService service;
    private final ProductsMapper mapper;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody ProductUpdate payload) throws ProductFieldNotReceivedException {
        Product productCreated = service.create(payload);

        return ResponseEntity.ok().body(mapper.toDto(productCreated));
    }

    @PostMapping("/import")
    @SneakyThrows
    public ResponseEntity<?> importProducts(@RequestBody @ModelAttribute FilePayload payload) {
        service.importProducts(payload);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok().body(mapper.toListDto(service.findAll(filters)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.toDto(service.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id, @RequestBody ProductUpdate product) throws ProductNotFoundException {
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
