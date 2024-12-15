package org.samuel.storemanagement.domain.preparation.preparation.exceptions;

import org.samuel.storemanagement.general.exceptions.ResourceNotFoundException;

public class PreparationNotFoundException extends ResourceNotFoundException {
    public PreparationNotFoundException() {
        super("Preparo não encontrado");
    }
}
