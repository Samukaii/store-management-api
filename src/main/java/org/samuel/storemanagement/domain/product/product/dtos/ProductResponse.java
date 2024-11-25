package org.samuel.storemanagement.domain.product.product.dtos;

import lombok.Data;
import org.samuel.storemanagement.domain.product.category.dtos.ProductCategoryViewResponse;

@Data
public class ProductResponse {
    long id;
    String name;
    String integrationName;
    Double price;
    ProductCategoryViewResponse category;
    double totalCost;
    double profit;
    double profitMargin;
    double suggestedPrice;
}
