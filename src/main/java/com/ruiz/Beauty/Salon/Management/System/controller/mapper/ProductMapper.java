package com.ruiz.Beauty.Salon.Management.System.controller.mapper;

import com.ruiz.Beauty.Salon.Management.System.controller.dto.ProductRequest;
import com.ruiz.Beauty.Salon.Management.System.controller.dto.ProductResponse;
import com.ruiz.Beauty.Salon.Management.System.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductMapper {

    public Product toProductEntity(ProductRequest request) {
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

    public ProductResponse toProductResponse(ProductRequest request) {
        ProductResponse response = ProductResponse.builder()
                .name(request.getName())
                .description(request.getDescription())
                .salePrice(request.getSalePrice())
                .costPrice(request.getCostPrice())
                .currentStock(request.getCurrentStock())
                .minimumStock(request.getMinimumStock())
                .build();

        return response;
    }
}
