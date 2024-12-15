package org.samuel.storemanagement.domain.product.category.exceptions;

import org.samuel.storemanagement.general.exceptions.ResourceNotFoundException;

public class ProductCategoryNotFoundException extends ResourceNotFoundException {
    public ProductCategoryNotFoundException() {
        super("Categoria não encontrada!");
    }
}
