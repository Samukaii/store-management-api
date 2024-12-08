package org.samuel.storemanagement.domain.preparation.preparation.services;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.preparation.ingredient.models.PreparationIngredient;
import org.samuel.storemanagement.domain.preparation.preparation.events.PreparationPublisher;
import org.samuel.storemanagement.domain.preparation.preparation.models.Preparation;
import org.samuel.storemanagement.domain.preparation.preparation.repositories.PreparationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreparationCalculationsService {
    private final PreparationRepository repository;
    private final PreparationPublisher publisher;

    public void calculateCosts(Preparation preparation) {
        double result = preparation.getIngredients()
                .stream()
                .mapToDouble(PreparationIngredient::getTotalCost)
                .sum();

        preparation.setTotalCost(result);

        calculateCostPerUnit(preparation);
    }

    private void calculateCostPerUnit(Preparation preparation) {
        if(preparation.getQuantity() <= 0) {
            preparation.setCostPerUnit(0d);
            return;
        }

        double result = preparation.getTotalCost() / preparation.getQuantity();

        preparation.setCostPerUnit(result);
    }

    public void save(Preparation preparation) {
        save(preparation, true);
    }

    public void save(Preparation preparation, Boolean emitChanges) {
        Preparation saved = repository.save(preparation);

        if (emitChanges) publisher.emitChanges(saved);
    }
}
