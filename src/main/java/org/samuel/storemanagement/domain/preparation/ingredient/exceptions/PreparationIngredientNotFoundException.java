package org.samuel.storemanagement.domain.preparation.ingredient.exceptions;

public class PreparationIngredientNotFoundException extends Exception {
    public PreparationIngredientNotFoundException() {
        super("Este ingrediente não foi cadastrado neste preparo!");
    }
}
