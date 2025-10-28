package com.ruiz.Beauty.Salon.Management.System.service.impl;

import com.ruiz.Beauty.Salon.Management.System.controller.dto.ProductRequest;
import com.ruiz.Beauty.Salon.Management.System.controller.dto.ProductResponse;
import com.ruiz.Beauty.Salon.Management.System.model.Product;
import com.ruiz.Beauty.Salon.Management.System.repository.InventoryRepository;
import com.ruiz.Beauty.Salon.Management.System.service.converter.ProductConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    /**
     * Tests successful product creation with valid data.
     *
     * Verifies that when all input data is valid, the product is properly
     * converted, saved to the repository, and returned as a response DTO.
     */
    @Test
    void createProduct_WhenIsSuccessful_ShouldReturnProductResponse() {
        // Arrange
        ProductRequest productRequest = new ProductRequest(
                "Product Test",
                "Product description",
                new BigDecimal(123.2),
                new BigDecimal(100),
                100,
                10
        );
        Product productEntity = new Product();
        Product savedProduct = new Product();
        ProductResponse expectedResponse = new ProductResponse();

        when(productConverter.toProduct(productRequest)).thenReturn(productEntity);
        when(inventoryRepository.save(productEntity)).thenReturn(savedProduct);
        when(productConverter.toProductResponse(savedProduct)).thenReturn(expectedResponse);

        // Act
        ProductResponse actualResponse = inventoryService.createProduct(productRequest);

        // Assert
        assertNotNull(actualResponse, "Response should not be null");
        assertEquals(expectedResponse.getId(), actualResponse.getId(), "Product ID should match");
        assertEquals(expectedResponse.getName(), actualResponse.getName(), "Product name should match");
        assertEquals(expectedResponse.getCostPrice(), actualResponse.getCostPrice(), "Cost price should match");

        // Verify interactions
        verify(productConverter).toProduct(productRequest);
        verify(inventoryRepository).save(productEntity);
        verify(productConverter).toProductResponse(savedProduct);
        verifyNoMoreInteractions(productConverter, inventoryRepository);
    }

    /**
     * Tests product creation with invalid product data.
     *
     * Verifies that validation errors are properly handled when
     * converter fails to create entity.
     */
    @Test
    void createProduct_WhenConverterFails_ShouldThrowIllegalArgumentException() {
        // Arrange
        ProductRequest productRequest = createValidProductRequest();

        when(productConverter.toProduct(productRequest))
                .thenThrow(new IllegalArgumentException("Invalid product data"));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> inventoryService.createProduct(productRequest));

        assertEquals("Invalid product data", exception.getMessage());
        verify(productConverter).toProduct(productRequest);
        verifyNoInteractions(inventoryRepository);
    }

    // Helper methods to create test data

    private ProductRequest createValidProductRequest() {
        return ProductRequest.builder()
                .name("Professional Shampoo")
                .description("Premium hair care product for professional use")
                .costPrice(new BigDecimal("8.50"))
                .salePrice(new BigDecimal("15.99"))
                .currentStock(100)
                .minimumStock(10)
                .build();
    }

    private ProductRequest createProductRequest() {
        ProductRequest product = new ProductRequest();
        product.setName("Professional Shampoo");
        product.setDescription("Premium hair care product for professional use");
        product.setCostPrice(new BigDecimal("8.50"));
        product.setSalePrice(new BigDecimal("15.99"));
        product.setCurrentStock(100);
        product.setMinimumStock(10);
        return product;
    }

    private Product createProductEntity() {
        Product product = new Product();
        product.setName("Professional Shampoo");
        product.setDescription("Premium hair care product for professional use");
        product.setCostPrice(new BigDecimal("8.50"));
        product.setSalePrice(new BigDecimal("15.99"));
        product.setCurrentStock(100);
        product.setMinimumStock(10);
        return product;
    }

    private Product createSavedProduct() {
        Product product = createProductEntity();
        product.setId(1L);
        return product;
    }

    private ProductResponse createProductResponse() {
        return ProductResponse.builder()
                .id(1L)
                .name("Professional Shampoo")
                .description("Premium hair care product for professional use")
                .costPrice(new BigDecimal("8.50"))
                .salePrice(new BigDecimal("15.99"))
                .currentStock(100)
                .minimumStock(10)
                .build();
    }


    @Test
    void updateProduct_WhenIsSuccessful() {

        // Arrange
        Long id = 1L;
        ProductRequest productRequest = createProductRequest();

        Product productEntity = createProductEntity();
        Product productSave = createSavedProduct();
        ProductResponse response = createProductResponse();

        when(inventoryRepository.findById(id)).thenReturn(Optional.of(productEntity));
        when(inventoryRepository.save(any(Product.class))).thenReturn(productSave);
        when(productConverter.toProductResponse(productSave)).thenReturn(response);

        //ACT
        ProductResponse productResponseresult = inventoryService.updateProduct(id, productRequest);

        // Verify interactions
        verify(inventoryRepository).save(productEntity);
        verify(productConverter).toProductResponse(productSave);
        verifyNoMoreInteractions(productConverter, inventoryRepository);

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