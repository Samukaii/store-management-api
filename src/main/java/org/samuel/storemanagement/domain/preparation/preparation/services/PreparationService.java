package org.samuel.storemanagement.domain.preparation.preparation.services;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.preparation.preparation.dtos.PreparationCreate;
import org.samuel.storemanagement.domain.preparation.preparation.dtos.PreparationUpdate;
import org.samuel.storemanagement.domain.preparation.preparation.events.PreparationPublisher;
import org.samuel.storemanagement.domain.preparation.preparation.exceptions.PreparationNotFoundException;
import org.samuel.storemanagement.domain.preparation.preparation.mappers.PreparationMapper;
import org.samuel.storemanagement.domain.preparation.preparation.models.Preparation;
import org.samuel.storemanagement.domain.preparation.preparation.repositories.PreparationRepository;
import org.samuel.storemanagement.general.filters.FilterSpecificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PreparationService {
    private final PreparationRepository repository;
    private final PreparationPublisher publisher;
    private final PreparationMapper mapper;
    private final PreparationCalculationsService calculationsService;
    private final FilterSpecificationService<Preparation> specificationService;

    public Preparation create(PreparationCreate payload) {
        return save(mapper.toModel(payload));
    }

    public Preparation findById(Long id) throws PreparationNotFoundException {
        Optional<Preparation> foundPreparation = repository.findById(id);

        return foundPreparation.orElseThrow(PreparationNotFoundException::new);
    }

    public List<Preparation> autocomplete(Map<String, String> filters) {
        var specification = specificationService.buildSpecification(filters);

        return repository.findAll(specification);
    }

    public Preparation updateById(Long id, PreparationUpdate payload) throws PreparationNotFoundException {
        Preparation foundPreparation = repository.findById(id).orElseThrow(PreparationNotFoundException::new);

        mapper.update(payload, foundPreparation);

        return save(foundPreparation);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Preparation> findAll() {
        return repository.findAll();
    }

    private Preparation save(Preparation preparation) {
        calculationsService.calculateCosts(preparation);

        Preparation result = repository.save(preparation);

        publisher.emitChanges(result);

        return result;
    }
}
