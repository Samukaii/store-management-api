package org.samuel.storemanagement.domain.rawMaterial.rawMaterial.exceptions;

public class RawMaterialRequiredFieldNotReceivedException extends Exception {
    public RawMaterialRequiredFieldNotReceivedException(String fieldName) {
        super("O campo " + fieldName + " é obrigatório");
    }
}
