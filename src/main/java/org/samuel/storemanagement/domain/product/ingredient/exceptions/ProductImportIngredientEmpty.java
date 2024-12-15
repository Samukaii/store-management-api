package org.samuel.storemanagement.domain.product.ingredient.exceptions;

import org.samuel.storemanagement.general.exceptions.ConflictException;

public class ProductImportIngredientEmpty extends ConflictException {
    public ProductImportIngredientEmpty() {
        super("O produto importado não possui nenhum ingrediente!");
    }
}

