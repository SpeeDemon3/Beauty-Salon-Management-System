package com.ruiz.Beauty.Salon.Management.System.service;

import com.ruiz.Beauty.Salon.Management.System.controller.dto.ProductRequest;
import com.ruiz.Beauty.Salon.Management.System.controller.dto.ProductResponse;
import com.ruiz.Beauty.Salon.Management.System.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface InventoryService {
    Boolean registerStockEntry(Long productId, Integer quantity);
    Boolean registerOutputStock(Long productId, Integer quantity);
    Boolean checkSufficientStock(Long productId, Integer quantityRequired);
    ProductResponse createProduct(ProductRequest productData);
    ProductResponse updateProduct(Long id, ProductRequest productData);
    ProductResponse getProductById(Long id);
    List<ProductResponse> getAllProducts();
    String generateLowStockAlert();
    String getTotalInventoryCost();
    Optional<Product> findByNameIgnoreCase(String name);
    List<Product> findAllByStockActualLessThanEqual(Integer stockLimit);
    List<Product> findByNameContainingIgnoreCase(String name);
}
