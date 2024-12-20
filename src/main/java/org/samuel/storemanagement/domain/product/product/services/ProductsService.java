package org.samuel.storemanagement.domain.product.product.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.product.category.exceptions.ProductCategoryNotFoundException;
import org.samuel.storemanagement.domain.product.category.models.ProductCategory;
import org.samuel.storemanagement.domain.product.category.services.ProductCategoryService;
import org.samuel.storemanagement.domain.product.product.dtos.ImportedProduct;
import org.samuel.storemanagement.domain.product.product.dtos.ProductCreate;
import org.samuel.storemanagement.domain.product.product.dtos.ProductUpdate;
import org.samuel.storemanagement.domain.product.product.events.ProductEventPublisher;
import org.samuel.storemanagement.domain.product.product.exceptions.ProductNotFoundException;
import org.samuel.storemanagement.domain.product.product.models.Product;
import org.samuel.storemanagement.domain.product.product.repositories.ProductsRepository;
import org.samuel.storemanagement.general.dto.FilePayload;
import org.samuel.storemanagement.general.filters.FilterSpecificationService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository repository;
    private final ProductCategoryService categoryService;
    private final ProductEventPublisher publisher;
    private final FilterSpecificationService<Product> filterSpecificationService;

    public Product create(ProductCreate payload) throws ProductCategoryNotFoundException {
        Product product = new Product();

        if(payload.getCategoryId() != null) {
            ProductCategory category = categoryService.findById(payload.getCategoryId());
            product.setCategory(category);
        }

        product.setName(payload.getName());
        product.setIntegrationName(payload.getIntegrationName());

        return save(product);
    }

    private void create(ImportedProduct imported) {
        Product existent = repository.findByIntegrationName(imported.getName()).orElse(null);

        if(existent != null) return;

        Product newProduct = new Product();

        newProduct.setName(imported.getName());
        newProduct.setIntegrationName(imported.getName());
        newProduct.setIngredients(new ArrayList<>());
        newProduct.setOrders(new ArrayList<>());

        Product existentProduct = repository.findByIntegrationName(imported.getName()).orElse(
                newProduct
        );

        existentProduct.setPrice(imported.getPrice());

        save(existentProduct);
    }

    public void importProducts(FilePayload payload) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        List<ImportedProduct> importedOrders = objectMapper.readValue(
                payload.getFile().getInputStream(),
                new TypeReference<>() {
                }
        );

        importedOrders.forEach(this::create);
    }

    public Product findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Product updateById(Long id, ProductUpdate product) throws ProductNotFoundException, ProductCategoryNotFoundException {
        Product productToUpdate = repository.findById(id).orElseThrow(ProductNotFoundException::new);

        publisher.emitPrePersist(productToUpdate);

        if (product.getName() != null)
            productToUpdate.setName(product.getName());

        if (product.getPrice() != null) {
            productToUpdate.setPrice(product.getPrice());
        }

        if (product.getIntegrationName() != null) {
            productToUpdate.setIntegrationName(product.getIntegrationName());
        }

        if(product.getCategoryId() != null) {
            ProductCategory category = categoryService.findById(product.getCategoryId());
            productToUpdate.setCategory(category);
        }


        return save(productToUpdate);
    }

    public Optional<Product> findByIntegrationName(String integrationName) {
        return repository.findByIntegrationName(integrationName);
    }

    public void deleteById(Long id) {
        delete(id);
    }

    public List<Product> findAll(Map<String, String> filters) {
        var specification = filterSpecificationService.buildSpecification(filters);

        return repository.findAll(specification);
    }

    public Product save(Product product) {

        Product result = repository.save(product);

        publisher.emitPostPersist(result);

        return result;
    }

    public void delete(Long id) {
        Product result = findById(id);

        publisher.emitDelete(result);

        repository.deleteById(id);
    }
}
