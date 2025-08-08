package org.samuel.storemanagement.domain.product.ingredient.services;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.preparation.preparation.exceptions.PreparationNotFoundException;
import org.samuel.storemanagement.domain.preparation.preparation.models.Preparation;
import org.samuel.storemanagement.domain.preparation.preparation.services.PreparationService;
import org.samuel.storemanagement.domain.product.ingredient.dtos.ProductIngredientCreate;
import org.samuel.storemanagement.domain.product.ingredient.dtos.ProductIngredientResponse;
import org.samuel.storemanagement.domain.product.ingredient.events.ProductIngredientEventPublisher;
import org.samuel.storemanagement.domain.product.ingredient.exceptions.ProductImportIngredientEmpty;
import org.samuel.storemanagement.domain.product.ingredient.exceptions.ProductIngredientNotFoundException;
import org.samuel.storemanagement.domain.product.ingredient.mappers.ProductIngredientMapper;
import org.samuel.storemanagement.domain.product.ingredient.models.ProductIngredient;
import org.samuel.storemanagement.domain.product.ingredient.repositories.ProductIngredientRepository;
import org.samuel.storemanagement.domain.product.product.dtos.ProductImportIngredients;
import org.samuel.storemanagement.domain.product.product.exceptions.ProductNotFoundException;
import org.samuel.storemanagement.domain.product.product.models.Product;
import org.samuel.storemanagement.domain.product.product.services.ProductsService;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.exceptions.RawMaterialNotFoundException;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.models.RawMaterial;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.services.RawMaterialService;
import org.samuel.storemanagement.general.filters.FilterSpecificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductIngredientService {
    private final ProductsService productsService;
    private final PreparationService preparationService;
    private final RawMaterialService rawMaterialService;
    private final ProductIngredientRepository repository;
    private final ProductIngredientEventPublisher publisher;
    private final ProductIngredientMapper mapper;
    private final FilterSpecificationService<ProductIngredient> specificationService;

    public ProductIngredient create(Long productId, ProductIngredientCreate payload) throws PreparationNotFoundException, RawMaterialNotFoundException {
        Product product = productsService.findById(productId);

        var productIngredient = mapper.toEntity(payload);

        productIngredient.setProduct(product);

        switch (payload.getIngredientType()) {
            case RAW_MATERIAL -> {
                RawMaterial foundRawMaterial = rawMaterialService.findById(payload.getRawMaterialId());

                productIngredient.setRawMaterial(foundRawMaterial);
            }
            case PREPARATION -> {
                Preparation preparation = preparationService.findById(payload.getPreparationId());

                productIngredient.setPreparation(preparation);
            }
            default -> {
                productIngredient.setCustomName(payload.getCustomName());
                productIngredient.setCustomCost(payload.getCustomCost());
            }
        }

        return recalculateAndSave(productIngredient);
    }

    public ProductIngredient create(Long productId, ProductIngredient ingredient)  {
        Product product = productsService.findById(productId);

        ingredient.setProduct(product);

        return recalculateAndSave(ingredient);
    }

    public ProductIngredient findById(Long productFoodInputId, Long productId) throws ProductIngredientNotFoundException {
        return repository.findByIdAndProductId(productFoodInputId, productId).orElseThrow(ProductIngredientNotFoundException::new);
    }

    public ProductIngredient updateById(Long productFoodInputId, Long productId, ProductIngredientCreate payload) throws ProductNotFoundException, PreparationNotFoundException, RawMaterialNotFoundException {
        ProductIngredient productIngredient = repository.findByIdAndProductId(productFoodInputId, productId).orElseThrow(ProductNotFoundException::new);

        mapper.updateEntity(productIngredient, payload);

        switch (payload.getIngredientType()) {
            case RAW_MATERIAL -> {
                RawMaterial foundRawMaterial = rawMaterialService.findById(payload.getRawMaterialId());

                productIngredient.setRawMaterial(foundRawMaterial);
            }
            case PREPARATION -> {
                Preparation preparation = preparationService.findById(payload.getPreparationId());

                productIngredient.setPreparation(preparation);
            }
            case PRODUCT -> {
                Product product = productsService.findById(payload.getIngredientProductId());

                productIngredient.setIngredientProduct(product);
            }
            default -> {
                productIngredient.setCustomName(payload.getCustomName());
                productIngredient.setCustomCost(payload.getCustomCost());
            }
        }

        return recalculateAndSave(productIngredient);
    }

    public void importIngredients(Long productId, ProductImportIngredients payload) throws ProductImportIngredientEmpty {
        List<ProductIngredient> ingredientsToImport = findAll(payload.getProductId());

        if(ingredientsToImport.isEmpty())
            throw new ProductImportIngredientEmpty();

        ingredientsToImport.forEach(ingredient -> create(productId, ingredient));
    }

    public void deleteById(Long ingredientId, Long productId) throws ProductIngredientNotFoundException {
        ProductIngredient productIngredient = findById(ingredientId, productId);

        repository.deleteByIdAndProductId(ingredientId, productId);
        repository.flush();

        publisher.emitChange(productIngredient);
    }

    public List<ProductIngredient> findAll(Long productId) {
        return repository.findAllByProductId(productId);
    }

    public List<ProductIngredientResponse> findAllCalculated(Long productId, Map<String, String> params) {
        var filters = specificationService.buildSpecification(params);

        return repository.getAllCalculatedProducts(productId, filters);
    }

    public ProductIngredient recalculateAndSave(ProductIngredient productIngredient) {
        productIngredient.setTotalCost(this.getCost(productIngredient));

        ProductIngredient result = repository.save(productIngredient);

        publisher.emitChange(productIngredient);

        return result;
    }

    private double getCost(ProductIngredient ingredient) {
        double quantity = ingredient.getQuantity() == null ? 0 : ingredient.getQuantity();

        return switch (ingredient.getIngredientType()) {
            case RAW_MATERIAL -> ingredient.getRawMaterial().getCostPerUnit() * quantity;
            case PREPARATION -> ingredient.getPreparation().getCostPerUnit() * quantity;
            default -> ingredient.getCustomCost();
        };
    }
}
