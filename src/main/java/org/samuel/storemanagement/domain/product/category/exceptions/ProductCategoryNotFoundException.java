package org.samuel.storemanagement.domain.product.category.exceptions;

public class ProductCategoryNotFoundException extends Exception {
    public ProductCategoryNotFoundException() {
        super("Categoria não encontrada!");
    }
}
