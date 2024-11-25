package org.samuel.storemanagement.domain.product.product.dtos;

import lombok.Data;

@Data
public class ProductUpdate {
    String name;
    String integrationName;
    Double price;
    Long categoryId;
}
