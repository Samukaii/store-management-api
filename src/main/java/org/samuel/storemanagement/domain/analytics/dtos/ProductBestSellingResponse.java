package org.samuel.storemanagement.domain.analytics.dtos;

import lombok.Builder;
import lombok.Data;
import org.samuel.storemanagement.domain.product.product.dtos.ProductResponse;

@Data
@Builder
public class ProductBestSellingResponse {
    Integer salesQuantity;
    Double totalBilled;
    ProductResponse product;
}
