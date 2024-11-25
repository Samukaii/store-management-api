package org.samuel.storemanagement.domain.product.ingredient.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.samuel.storemanagement.domain.preparation.preparation.models.Preparation;
import org.samuel.storemanagement.domain.preparation.preparation.services.PreparationService;
import org.samuel.storemanagement.domain.product.ingredient.dtos.ProductIngredientCreate;
import org.samuel.storemanagement.domain.product.ingredient.events.ProductIngredientEventPublisher;
import org.samuel.storemanagement.domain.product.ingredient.exceptions.ProductIngredientNotFoundException;
import org.samuel.storemanagement.domain.product.ingredient.mappers.ProductIngredientMapper;
import org.samuel.storemanagement.domain.product.ingredient.models.ProductIngredient;
import org.samuel.storemanagement.domain.product.ingredient.repositories.ProductIngredientRepository;
import org.samuel.storemanagement.domain.product.product.exceptions.ProductNotFoundException;
import org.samuel.storemanagement.domain.product.product.models.Product;
import org.samuel.storemanagement.domain.product.product.services.ProductsService;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.models.RawMaterial;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.services.RawMaterialService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductIngredientService {
    private final ProductsService productsService;
    private final PreparationService preparationService;
    private final RawMaterialService rawMaterialService;
    private final ProductIngredientRepository repository;
    private final ProductIngredientEventPublisher publisher;
    private final ProductIngredientMapper mapper;

    @SneakyThrows
    public ProductIngredient create(Long productId, ProductIngredientCreate payload)  {
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

    @SneakyThrows
    public ProductIngredient findById(Long productFoodInputId, Long productId) {
        return repository.findByIdAndProductId(productFoodInputId, productId).orElseThrow(ProductIngredientNotFoundException::new);
    }

    @SneakyThrows
    public ProductIngredient updateById(Long productFoodInputId, Long productId, ProductIngredientCreate payload) {
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
            default -> {
                productIngredient.setCustomName(payload.getCustomName());
                productIngredient.setCustomCost(payload.getCustomCost());
            }
        }

        return recalculateAndSave(productIngredient);
    }

    public void deleteById(Long ingredientId, Long productId) {
        ProductIngredient productIngredient = findById(ingredientId, productId);

        repository.deleteByIdAndProductId(ingredientId, productId);
        repository.flush();

        publisher.emitChange(productIngredient);
    }

    public List<ProductIngredient> findAll(Long productId) {
        return repository.findAllByProductId(productId);
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
