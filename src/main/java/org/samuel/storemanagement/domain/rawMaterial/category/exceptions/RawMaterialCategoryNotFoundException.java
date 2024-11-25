package org.samuel.storemanagement.domain.rawMaterial.category.exceptions;

public class RawMaterialCategoryNotFoundException extends Exception {
    public RawMaterialCategoryNotFoundException() {
        super("Categoria não encontrada!");
    }
}
