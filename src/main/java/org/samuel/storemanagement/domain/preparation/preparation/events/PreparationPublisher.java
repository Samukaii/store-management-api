package org.samuel.storemanagement.domain.preparation.preparation.events;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.preparation.preparation.models.Preparation;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PreparationPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void emitChanges(Preparation material) {
        applicationEventPublisher.publishEvent(new PreparationChangeEvent(material));
    }
}
