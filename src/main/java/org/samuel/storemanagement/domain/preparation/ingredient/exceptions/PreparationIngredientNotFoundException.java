package org.samuel.storemanagement.domain.preparation.ingredient.exceptions;

import org.samuel.storemanagement.general.exceptions.ResourceNotFoundException;

public class PreparationIngredientNotFoundException extends ResourceNotFoundException {
    public PreparationIngredientNotFoundException() {
        super("Este ingrediente não foi cadastrado neste preparo!");
    }
}
