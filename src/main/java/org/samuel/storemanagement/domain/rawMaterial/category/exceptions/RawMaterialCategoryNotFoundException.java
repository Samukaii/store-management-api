package org.samuel.storemanagement.domain.rawMaterial.category.exceptions;

import org.samuel.storemanagement.general.exceptions.ResourceNotFoundException;

public class RawMaterialCategoryNotFoundException extends ResourceNotFoundException {
    public RawMaterialCategoryNotFoundException() {
        super("Categoria não encontrada!");
    }
}
