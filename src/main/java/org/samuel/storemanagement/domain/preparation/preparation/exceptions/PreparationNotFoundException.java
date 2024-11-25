package org.samuel.storemanagement.domain.preparation.preparation.exceptions;

public class PreparationNotFoundException extends Exception {
    public PreparationNotFoundException() {
        super("Preparo não encontrado");
    }
}
