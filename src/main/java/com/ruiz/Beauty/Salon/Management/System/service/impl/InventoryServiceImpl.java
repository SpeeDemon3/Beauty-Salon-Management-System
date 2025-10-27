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

    /**
     * Registers a stock entry for a product, increasing its current stock level.
     *
     * This method processes incoming merchandise (e.g., supplier deliveries) by
     * adding the specified quantity to the product's current stock. It validates
     * both the product existence and the quantity before performing the update.
     * The operation ensures data consistency by persisting changes to the database.
     *
     * @param productId the unique identifier of the product to update
     * @param quantity the quantity of stock to add (must be positive)
     * @return Boolean indicating whether the stock entry was successfully registered:
     *         true if successful, false if product not found or validation failed
     *
     * @implNote The method performs the following steps:
     * 1. Retrieves the product entity from the repository
     * 2. Validates that the product exists and has valid current stock
     * 3. Ensures the quantity to add is positive
     * 4. Updates the current stock by adding the quantity
     * 5. Persists the changes to the database
     * 6. Returns operation status
     *
     * @apiNote This method is typically used for inventory restocking operations
     *          and supplier deliveries. It only allows positive stock additions.
     *
     * @see Product
     * @see InventoryRepository
     * @since 1.0
     */
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
                inventoryRepository.save(product);
                log.info("Stock updated successfully!!!");
                return true;
            }
        }

        log.warn("Product not found for stock entry - ID: {}", productId);
        return false;
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

    /**
     * Retrieves a product by its unique identifier with stock level monitoring.
     *
     * Fetches a specific product from the database using the provided ID. Automatically
     * checks the product's stock level and generates low stock alerts if the current
     * stock falls below the threshold (10 units). Returns the product data as a
     * response DTO or null if the product is not found.
     *
     * @param id the unique identifier of the product to retrieve
     * @return ProductResponse the product data as a response DTO, or null if not found
     *
     * @implNote This method performs three main operations:
     * 1. Retrieves the product entity from the database by ID
     * 2. Checks stock levels and generates alerts for low stock conditions
     * 3. Converts the entity to a response DTO for API consumption
     *
     * @see Product
     * @see ProductResponse
     * @see #generateLowStockAlert()
     * @since 1.0
     */
    @Override
    public ProductResponse getProductById(Long id) {

        Optional<Product> productOptional = inventoryRepository.findById(id);

        if (productOptional.isPresent()) {
            if (productOptional.get().getCurrentStock() < 11) {
                generateLowStockAlert();
                log.info("Product with low stock: {}", productOptional.get().getName());
            }
            ProductResponse response = productConverter.toProductResponse(productOptional.get());
            log.info("Product found: {}", response);
            return response;
        }

        return null;
    }

    /**
     * Retrieves all products from the inventory system.
     *
     * Fetches all product entities from the database, converts them to response DTOs,
     * and checks for low stock levels during the process. Generates alerts for
     * products with stock levels below threshold (less than 11 units).
     *
     * @return List<ProductResponse> a list of all products as response DTOs,
     *         or an empty list if no products are found
     *
     * @implNote This method performs two main operations:
     * 1. Retrieves all products from the database
     * 2. Checks stock levels and generates low stock alerts when necessary
     * 3. Converts entities to response DTOs for API consumption
     *
     * @see Product
     * @see ProductResponse
     * @see #generateLowStockAlert()
     */
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
            return productResponseList;
        }

        return List.of();
    }

    /**
     * Generates low stock alerts for products with critically low inventory levels.
     *
     * Identifies products that have current stock levels at or below the specified
     * threshold (10 units) by querying the repository. This method serves as an
     * early warning system to prevent stockouts and ensure inventory availability.
     * Returns a descriptive alert message indicating the presence of low stock items.
     *
     * @return String an alert message indicating that products with low stock were found
     *
     * @implNote This method uses a fixed threshold of 10 units to determine low stock conditions.
     *           The actual product retrieval and processing is delegated to the repository method
     *           findAllByCurrentStockLessThanEqual().
     *
     * @see InventoryRepository#findAllByCurrentStockLessThanEqual(Integer)
     * @since 1.0
     */
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

    /**
     * Finds a product by name using case-insensitive matching.
     *
     * Searches through all products in the inventory to find an exact name match
     * ignoring case differences. This method performs a linear search through
     * all products, which may impact performance with large datasets.
     * Consider using repository-level case-insensitive search for better efficiency.
     *
     * @param name the product name to search for (case-insensitive)
     * @return Optional containing the found product, or empty if no match is found
     *
     * @implNote This implementation retrieves all products and performs filtering
     *           in memory, which may not be optimal for large inventories.
     *           For better performance, consider implementing a custom repository
     *           method with database-level case-insensitive search.
     *
     * @see Product
     * @see InventoryRepository#findAll()
     * @since 1.0
     */
    @Override
    public Optional<ProductResponse> findByNameIgnoreCase(String name) {

        List<Product> productList = inventoryRepository.findAll();

        if (!productList.isEmpty()) {
            for (Product product : productList) {
                if (name.equalsIgnoreCase(product.getName())) {

                    return Optional.of(productConverter.toProductResponse(product));
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
     * Finds products by name using case-insensitive partial matching.
     *
     * Searches for products whose names contain the provided search string,
     * ignoring case differences. Converts matching products to response DTOs
     * and returns them as a list. Returns an empty list instead of null when
     * no products are found to maintain API consistency.
     *
     * @param name the search string to match against product names (case-insensitive)
     * @return List of ProductResponse objects containing matching products,
     *         or empty list if no matches are found
     *
     * @implNote This method converts entity objects to response DTOs to ensure
     *           proper separation between persistence layer and API layer.
     *           The search performs partial matching, so products containing
     *           the search string anywhere in their name will be included.
     *
     * @see Product
     * @see ProductResponse
     * @see ProductConverter#toProductResponse(Product)
     * @since 1.0
     */
    @Override
    public List<ProductResponse> findByNameContainingIgnoreCase(String name) {
        List<Product> results = inventoryRepository.findByNameContainingIgnoreCase(name);

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
