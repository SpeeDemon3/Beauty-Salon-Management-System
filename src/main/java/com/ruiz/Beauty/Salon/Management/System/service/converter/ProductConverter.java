package com.ruiz.Beauty.Salon.Management.System.service.converter;

import com.ruiz.Beauty.Salon.Management.System.controller.dto.ProductRequest;
import com.ruiz.Beauty.Salon.Management.System.controller.dto.ProductResponse;
import com.ruiz.Beauty.Salon.Management.System.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {

    public Product toProduct(ProductRequest request) {
        Product productEntity = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .salePrice(request.getSalePrice())
                .costPrice(request.getCostPrice())
                .currentStock(request.getCurrentStock())
                .minimumStock(request.getMinimumStock())
                .build();

        return productEntity;

    }

    public ProductResponse toProductResponse(Product entity) {
        ProductResponse response = ProductResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .salePrice(entity.getSalePrice())
                .costPrice(entity.getCostPrice())
                .currentStock(entity.getCurrentStock())
                .minimumStock(entity.getMinimumStock())
                .build();

        return response;
    }
}
