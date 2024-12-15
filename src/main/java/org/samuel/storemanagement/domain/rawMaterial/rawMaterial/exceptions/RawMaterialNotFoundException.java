package org.samuel.storemanagement.domain.rawMaterial.rawMaterial.exceptions;

import org.samuel.storemanagement.general.exceptions.ResourceNotFoundException;

public class RawMaterialNotFoundException extends ResourceNotFoundException {
    public RawMaterialNotFoundException() {
        super("Insumo não encontrado!");
    }
}
