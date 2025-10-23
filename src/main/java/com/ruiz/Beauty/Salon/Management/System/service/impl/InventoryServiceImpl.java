package com.ruiz.Beauty.Salon.Management.System.service.impl;

import com.ruiz.Beauty.Salon.Management.System.controller.dto.ProductRequest;
import com.ruiz.Beauty.Salon.Management.System.controller.dto.ProductResponse;
import com.ruiz.Beauty.Salon.Management.System.model.Product;
import com.ruiz.Beauty.Salon.Management.System.repository.InventoryRepository;
import com.ruiz.Beauty.Salon.Management.System.service.InventoryService;
import com.ruiz.Beauty.Salon.Management.System.service.converter.ProductConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductConverter productConverter;

    //OK
    @Override
    public Boolean registerStockEntry(Long productId, Integer quantity) {
        /**
         * Registra la llegada de mercancía (ej. un pedido a proveedor), aumentando el stock actual del producto.
         *
         * Busca el Producto, valida que cantidad sea positiva y suma al stockActual.
         */

        Optional<Product> productOptional = inventoryRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            if (product.getCurrentStock() >= 0 && quantity > 0) {
                product.setCurrentStock(product.getCurrentStock() + quantity);
                log.info("Stock updated successfully!!!");
                return true;
            }
        }


        return null;
    }

    // OK
    @Override
    public Boolean registerOutputStock(Long productId, Integer quantity) {
        /**
         * Registra una deducción de stock por venta o uso en un servicio. Disminuye el stock actual.
         * Validación Crítica: Debe llamar a verificarStockSuficiente() antes de la deducción.
         */

        if(checkSufficientStock(productId, quantity)) {
            Product product = inventoryRepository.findById(productId).get();
            product.setCurrentStock(product.getCurrentStock() - quantity);
            return true;
        }

        log.error("There is not enough stock!!!");

        return false;
    }

    //OK
    private Boolean checkSufficientStock(Long productId, Integer quantityRequired) {
        /**
         * Método auxiliar privado que verifica si el stockActual de un producto cubre la cantidad solicitada.
         * Devuelve true/false. Usado internamente por registrarSalidaStock.
         */

        Optional<Product> productOptional = inventoryRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            return product.getCurrentStock() >= quantityRequired;
        }

        return false;
    }

    /**
     * Creates a new product in the inventory system.
     *
     * Converts the provided product request data to a Product entity, persists it
     * to the database, and returns the saved product as a response DTO.
     * Includes logging for monitoring the creation process.
     *
     * @param productData the product request data containing product information
     * @return ProductResponse the created product as a response DTO
     */
    @Override
    public ProductResponse createProduct(ProductRequest productData) {

        Product productEntity = productConverter.toProduct(productData);
        log.info("Converted to CarEntity: {}", productData);

        Product productSave = inventoryRepository.save(productEntity);
        log.info("Saved CarEntity: {}", productSave);

        return productConverter.toProductResponse(productSave);
    }

    /**
     * Updates an existing product with the provided data.
     *
     * Modifies product properties such as sale price, cost price, or minimum stock.
     * Retrieves the product by ID, applies partial updates to non-null fields,
     * and persists the changes to the database. Returns the updated product
     * or null if the product is not found.
     *
     * @param id the unique identifier of the product to update
     * @param productData the product data containing fields to update
     * @return ProductResponse the updated product data, or null if product not found
     *
     * @implNote This method implements partial updates - only non-null fields
     *           from the productData will be updated. Existing values for null
     *           fields will remain unchanged.
     *
     * @see Product
     * @see ProductRequest
     * @see ProductResponse
     */
    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productData) {
        /**
         * Modifica datos como el precioVenta, precioCosto o stockMinimo.
         * Busca el producto por ID, aplica las actualizaciones y guarda.
         */

        Optional<Product> productOptional = inventoryRepository.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            if (productData.getName() != null) {
                product.setName(productData.getName());
            }

            if (productData.getDescription() != null) {
                product.setDescription(productData.getDescription());
            }

            if (productData.getSalePrice() != null) {
                product.setSalePrice(productData.getSalePrice());
            }

            if (productData.getCostPrice() != null) {
                product.setCostPrice(productData.getCostPrice());
            }

            if (productData.getCurrentStock() != null) {
                product.setCurrentStock(productData.getCurrentStock());
            }

            if (productData.getMinimumStock() != null) {
                product.setMinimumStock(productData.getMinimumStock());
            }

            Product productUpdate = inventoryRepository.save(product);

            return productConverter.toProductResponse(productUpdate);
        }

        log.error("The product cannot be updated due to some error.");
        return null;
    }

    //OK
    @Override
    public ProductResponse getProductById(Long id) {

        Optional<Product> productOptional = inventoryRepository.findById(id);

        if (productOptional.isPresent()) {
            if (productOptional.get().getCurrentStock() < 11) {
                generateLowStockAlert();
                log.info("Product with low stock: {}", productOptional.get().getName());
            }
            return productConverter.toProductResponse(productOptional.get());
        }

        return null;
    }

    //OK
    @Override
    public List<ProductResponse> getAllProducts() {

        List<Product> productListEntity = inventoryRepository.findAll();
        List<ProductResponse> productResponseList = new ArrayList<>();

        if (!productListEntity.isEmpty()) {
            for (Product product : productListEntity) {
                if (product.getCurrentStock() < 11) {
                    generateLowStockAlert();
                    log.info("Product with low stock: {}", product.getName());
                }
                productResponseList.add(productConverter.toProductResponse(product));
            }
        }

        return List.of();
    }

    //OK
    @Override
    public String generateLowStockAlert() {
        /**
         * Devuelve una lista de productos que están por debajo de su stockMinimo.
         * Llama a un método personalizado en el ProductoRepository: findAllByStockActualLessThan(stockMinimo).
         */

        findAllByCurrentStockLessThanEqual(10);

        return "There are products with low stock!!!!";
    }

    //OK
    @Override
    public String getTotalInventoryCost() {
        /**
         * Calcula el valor total de todos los productos en stock usando su precioCosto.
         * Itera sobre todos los productos y suma (stockActual * precioCosto).
         */

        List<Product> productList = inventoryRepository.findAll();

        // Inicializar con BigDecimal.ZERO en lugar de new BigDecimal(0)
        BigDecimal result = BigDecimal.ZERO;

        for (Product product : productList) {
            // Verificar que los valores no sean nulos antes de operar
            if (product.getCostPrice() != null && product.getCurrentStock() != null) {
                // Calcular el costo por producto: precioCosto * stockActual
                BigDecimal productCost = product.getCostPrice()
                        .multiply(BigDecimal.valueOf(product.getCurrentStock()));

                // Sumar al resultado total usando add() en lugar de +=
                result = result.add(productCost);
            }
        }

        // Aplicar el redondeo al resultado final (no en cada iteración)
        result = result.setScale(2, RoundingMode.HALF_UP);

        // Retornar el resultado como String
        return result.toString();

    }

    //OK
    /**
     * Busca un producto por su nombre ignorando mayúsculas/minúsculas.
     * Útil para búsquedas en el catálogo.
     */
    @Override
    public Optional<Product> findByNameIgnoreCase(String name) {

        List<Product> productList = inventoryRepository.findAll();

        if (!productList.isEmpty()) {
            for (Product product : productList) {
                if (name.equalsIgnoreCase(product.getName())) {

                    return Optional.of(product);
                }
            }
        }

        log.info("Product not found with name: {}", name);
        return Optional.empty();
    }

    //OK
    /**
     * Obtiene una lista de productos cuyo stock actual es menor
     * o igual a un nivel específico. Esencial para las alertas.
     *
     * @param stockLimit El umbral mínimo a comparar (normalmente el campo 'stockMinimo' de la entidad).
     * @return Lista de productos con stock bajo.
     */
    @Override
    public List<Product> findAllByCurrentStockLessThanEqual(Integer stockLimit) {

        List<Product> productList = inventoryRepository.findAll();

        if (!productList.isEmpty()) {
            List<Product> lowStockProducts = new ArrayList<>();

            for (Product product : productList) {
                if (product.getCurrentStock() <= stockLimit) {
                    lowStockProducts.add(product);
                }
            }

            return lowStockProducts;
        }

        return List.of();
    }

    /**
     * Busca productos cuyo nombre contenga la cadena proporcionada.
     * Útil para la función de autocompletar o búsqueda en el TPV.
     */
    @Override
    public List<ProductResponse> findByNameContainingIgnoreCase(String name) {
        List<Product> results = inventoryRepository.findByNameContainingIgnoreCase("shampoo");

        if (!results.isEmpty()) {
            List<ProductResponse> productResponseList = new ArrayList<>();

            for (Product product : results) {
                productResponseList.add(productConverter.toProductResponse(product));
            }

            log.info("Products found with name containing '{}': {}", name, productResponseList);
            return productResponseList;

        } else {
            log.error("No products found with name containing: {}", name);
            return null;
        }

    }
}
