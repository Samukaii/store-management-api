package org.samuel.storemanagement.domain.preparation.ingredient.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.samuel.storemanagement.domain.preparation.ingredient.dtos.PreparationIngredientCreate;
import org.samuel.storemanagement.domain.preparation.ingredient.enumerations.PreparationIngredientType;
import org.samuel.storemanagement.domain.preparation.ingredient.events.PreparationIngredientEventPublisher;
import org.samuel.storemanagement.domain.preparation.ingredient.exceptions.PreparationIngredientNotFoundException;
import org.samuel.storemanagement.domain.preparation.ingredient.mappers.PreparationIngredientMapper;
import org.samuel.storemanagement.domain.preparation.ingredient.models.PreparationIngredient;
import org.samuel.storemanagement.domain.preparation.ingredient.repositories.PreparationIngredientRepository;
import org.samuel.storemanagement.domain.preparation.preparation.models.Preparation;
import org.samuel.storemanagement.domain.preparation.preparation.services.PreparationService;
import org.samuel.storemanagement.domain.product.product.exceptions.ProductNotFoundException;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.models.RawMaterial;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.services.RawMaterialService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreparationIngredientService {
    private final PreparationService preparationService;
    private final RawMaterialService rawMaterialService;
    private final PreparationIngredientRepository repository;
    private final PreparationIngredientEventPublisher publisher;
    private final PreparationIngredientCalculationsService calculationsService;
    private final PreparationIngredientMapper mapper;

    @SneakyThrows
    public PreparationIngredient create(Long productId, PreparationIngredientCreate payload)  {
        Preparation preparation = preparationService.findById(productId);

        PreparationIngredient preparationIngredient = mapper.toEntity(payload);
        preparationIngredient.setPreparation(preparation);

        if(payload.getIngredientType() == PreparationIngredientType.RAW_MATERIAL) {
            RawMaterial rawMaterial = rawMaterialService.findById(payload.getRawMaterialId());
            preparationIngredient.setRawMaterial(rawMaterial);
        }
        else {
            preparationIngredient.setCustomCost(payload.getCustomCost());
            preparationIngredient.setCustomName(payload.getCustomName());
        }

        return save(preparationIngredient);
    }

    @SneakyThrows
    public PreparationIngredient findById(Long productFoodInputId, Long productId) {
        return repository.findByIdAndPreparationId(productFoodInputId, productId).orElseThrow(PreparationIngredientNotFoundException::new);
    }

    @SneakyThrows
    public PreparationIngredient updateById(Long productFoodInputId, Long productId, PreparationIngredientCreate payload) {
        PreparationIngredient preparationIngredient = repository.findByIdAndPreparationId(productFoodInputId, productId).orElseThrow(ProductNotFoundException::new);

        mapper.updateEntity(preparationIngredient, payload);

        if(payload.getRawMaterialId() != null) {
            RawMaterial rawMaterial = rawMaterialService.findById(payload.getRawMaterialId());
            preparationIngredient.setRawMaterial(rawMaterial);
        }

        return save(preparationIngredient);
    }

    public void deleteById(Long ingredientId, Long productId) {
        PreparationIngredient preparationIngredient = findById(ingredientId, productId);

        repository.deleteByIdAndPreparationId(ingredientId, productId);
        repository.flush();

        publisher.emitChange(preparationIngredient);
    }

    public List<PreparationIngredient> findAll(Long productId) {
        return repository.findAllByPreparationId(productId);
    }

    public PreparationIngredient save(PreparationIngredient preparationIngredient) {
        calculationsService.calculateTotalCost(preparationIngredient);

        PreparationIngredient result = repository.save(preparationIngredient);

        publisher.emitChange(preparationIngredient);

        return result;
    }
}
