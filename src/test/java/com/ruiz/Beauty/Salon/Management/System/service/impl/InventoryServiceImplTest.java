package com.ruiz.Beauty.Salon.Management.System.service.impl;

import com.ruiz.Beauty.Salon.Management.System.model.Product;
import com.ruiz.Beauty.Salon.Management.System.repository.InventoryRepository;
import com.ruiz.Beauty.Salon.Management.System.service.converter.ProductConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private ProductConverter productConverter;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @Test
    void registerStockEntry_ShouldReturnTrue_WhenValidInput() {
        // Arrange
        Long productId = 1L;
        Integer quantity = 5;
        Product product = new Product();
        product.setId(productId);
        product.setCurrentStock(10);
        product.setCostPrice(new BigDecimal("25.50"));

        when(inventoryRepository.findById(productId)).thenReturn(Optional.of(product));
        when(inventoryRepository.save(any(Product.class))).thenReturn(product);

        // Act
        Boolean result = inventoryService.registerStockEntry(productId, quantity);

        // Assert
        assertTrue(result);
        verify(inventoryRepository).save(product);
        assertEquals(15, product.getCurrentStock()); // 10 + 5
    }

    @Test
    void registerStockEntry_ShouldReturnFalse_WhenProductNotFound() {
        // Arrange
        Long productId = 999L;
        Integer quantity = 5;

        when(inventoryRepository.findById(productId)).thenReturn(Optional.empty());

        // Act
        Boolean result = inventoryService.registerStockEntry(productId, quantity);

        // Assert
        assertFalse(result);
        verify(inventoryRepository, never()).save(any());
    }

    @Test
    void registerOutputStock_ShouldReturnFalse_WhenProductNotFound() {

        // Arrange
        Long productId = 999L;
        Integer quantity = 5;

        when(inventoryRepository.findById(productId)).thenReturn(Optional.empty());

        // Act
        Boolean result = inventoryService.registerOutputStock(productId, quantity);

        // Assert
        assertFalse(result);
        verify(inventoryRepository, never()).save(any());

    }

    @Test
    void registerOutputStock_ShouldReturnTrue_WhenValidInput() {

        // Arrange
        Long productId = 1L;
        Integer quantity = 5;
        Product product = new Product();
        product.setId(productId);
        product.setCurrentStock(10);
        product.setCostPrice(new BigDecimal("25.50"));

        when(inventoryRepository.findById(productId)).thenReturn(Optional.of(product));
        when(inventoryRepository.save(any(Product.class))).thenReturn(product);

        // Act
        Boolean result = inventoryService.registerOutputStock(productId, quantity);

        // Assert
        assertTrue(result);
        verify(inventoryRepository).save(product);
        assertEquals(5, product.getCurrentStock()); // 10 - 5

    }

    @Test
    void createProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void getProductById() {
    }

    @Test
    void getAllProducts() {
    }

    @Test
    void generateLowStockAlert() {
    }

    @Test
    void getTotalInventoryCost() {
    }

    @Test
    void findByNameIgnoreCase() {
    }

    @Test
    void findAllByCurrentStockLessThanEqual() {
    }

    @Test
    void findByNameContainingIgnoreCase() {
    }
}