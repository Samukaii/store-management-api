package org.samuel.storemanagement.domain.rawMaterial.rawMaterial.events;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.models.RawMaterial;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RawMaterialPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void emitChanges(RawMaterial foodInput) {
        applicationEventPublisher.publishEvent(new RawMaterialChangeEvent(foodInput));
    }
}
