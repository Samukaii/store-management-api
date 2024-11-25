package org.samuel.storemanagement.domain.preparation.preparation.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.samuel.storemanagement.domain.preparation.preparation.dtos.PreparationCreate;
import org.samuel.storemanagement.domain.preparation.preparation.dtos.PreparationUpdate;
import org.samuel.storemanagement.domain.preparation.preparation.events.PreparationPublisher;
import org.samuel.storemanagement.domain.preparation.preparation.exceptions.PreparationNotFoundException;
import org.samuel.storemanagement.domain.preparation.preparation.mappers.PreparationMapper;
import org.samuel.storemanagement.domain.preparation.preparation.models.Preparation;
import org.samuel.storemanagement.domain.preparation.preparation.repositories.PreparationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PreparationService {
    private final PreparationRepository repository;
    private final PreparationPublisher publisher;
    private final PreparationMapper mapper;
    private final PreparationCalculationsService calculationsService;

    @SneakyThrows
    public Preparation create(PreparationCreate payload) {
        return save(mapper.toModel(payload));
    }

    public Preparation findById(Long id) throws PreparationNotFoundException {
        Optional<Preparation> foundPreparation = repository.findById(id);

        return foundPreparation.orElseThrow(PreparationNotFoundException::new);
    }

    public List<Preparation> autocomplete(Optional<String> search) {
        return search.map(repository::searchAllByText).orElseGet(this::findAll);
    }

    @SneakyThrows
    public Preparation updateById(Long id, PreparationUpdate payload) {
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
