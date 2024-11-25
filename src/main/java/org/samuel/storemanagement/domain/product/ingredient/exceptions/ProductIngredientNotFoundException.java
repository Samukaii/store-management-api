package org.samuel.storemanagement.domain.product.ingredient.exceptions;

public class ProductIngredientNotFoundException extends Exception {
    public ProductIngredientNotFoundException() {
        super("Este ingrediente não foi cadastrado neste produto!");
    }
}
