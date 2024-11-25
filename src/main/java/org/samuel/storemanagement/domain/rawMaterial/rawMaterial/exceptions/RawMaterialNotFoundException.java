package org.samuel.storemanagement.domain.rawMaterial.rawMaterial.exceptions;

public class RawMaterialNotFoundException extends Exception {
    public RawMaterialNotFoundException() {
        super("Insumo não encontrado!");
    }
}
