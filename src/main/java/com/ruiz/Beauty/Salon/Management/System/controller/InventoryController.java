package com.ruiz.Beauty.Salon.Management.System.controller;

import com.ruiz.Beauty.Salon.Management.System.controller.dto.ProductRequest;
import com.ruiz.Beauty.Salon.Management.System.controller.dto.ProductResponse;
import com.ruiz.Beauty.Salon.Management.System.controller.mapper.ProductMapper;
import com.ruiz.Beauty.Salon.Management.System.model.Product;
import com.ruiz.Beauty.Salon.Management.System.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ProductMapper productMapper;


    // --- 1. GESTIÓN DEL CATÁLOGO (CRUD BÁSICO) ---

    /**
     * Creates a new product in the inventory system.
     *
     * This endpoint accepts product data in the request body and attempts to persist it
     * to the database. If successful, returns the created product with HTTP 201 status.
     * Handles invalid input by returning an appropriate error response.
     *
     * @param productData the product information to be created, provided in the request body
     * @return ResponseEntity containing the created product with HTTP 201 status on success,
     *         or HTTP 500 status if an illegal argument error occurs during processing
     * @throws IllegalArgumentException if the provided product data contains invalid values
     *                                  that violate business logic constraints
     *
     * @apiNote This endpoint is part of the product inventory management system and
     *          requires valid product data including name, price, and stock information.
     *
     * @example
     * // Sample request body:
     * {
     *   "name": "Shampoo",
     *   "description": "Hair care product",
     *   "price": 15.99,
     *   "stock": 100
     * }
     *
     * // Sample response (201 Created):
     * {
     *   "id": 1,
     *   "name": "Shampoo",
     *   "description": "Hair care product",
     *   "price": 15.99,
     *   "stock": 100,
     *   "createdAt": "2023-10-05T10:30:00Z"
     * }
     */
    @PostMapping("/products")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productData) {

        try {
            log.info("Product save from controller");
            return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.createProduct(productData));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    /**
     * Updates an existing product in the inventory system.
     *
     * This endpoint updates a product identified by the provided ID with the new data
     * from the request body. If the product is found and successfully updated,
     * returns the updated product data. If the product is not found, returns a 404 status.
     *
     * @param id the unique identifier of the product to update
     * @param productData the updated product information
     * @return ResponseEntity containing the updated product with HTTP 200 status on success,
     *         or HTTP 404 status if no product is found with the provided ID
     * @throws Exception if an error occurs during the update process
     *
     * @apiNote The product must exist in the system for the update to succeed.
     *          Partial updates are supported - only provided fields will be updated.
     *
     * @example
     * // Sample request: PUT /api/products/1
     * {
     *   "name": "Updated Shampoo",
     *   "price": 18.99,
     *   "stock": 150
     * }
     *
     * // Sample response (200 OK):
     * {
     *   "id": 1,
     *   "name": "Updated Shampoo",
     *   "price": 18.99,
     *   "stock": 150,
     *   "updatedAt": "2023-10-05T14:30:00Z"
     * }
     */
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productData) {
        try {
            return ResponseEntity.ok(inventoryService.updateProduct(id, productData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    /**
     * Retrieves all products from the inventory system.
     *
     * This endpoint returns a complete list of all products available in the system.
     * Handles various error scenarios including not found errors and data access issues.
     * Returns appropriate HTTP status codes based on the operation outcome.
     *
     * @return ResponseEntity containing a list of products with HTTP 200 status on success,
     *         HTTP 404 if no products are found, or HTTP 500 for internal server errors
     * @throws HttpClientErrorException.NotFound when no products are found in the system
     * @throws DataAccessException when there are issues accessing the database
     *
     * @apiNote This endpoint provides complete inventory visibility and includes
     *          automatic low stock monitoring in the background.
     *
     * @example
     * // Success response (200 OK):
     * [
     *   {
     *     "id": 1,
     *     "name": "Shampoo",
     *     "price": 15.99,
     *     "stock": 25
     *   },
     *   {
     *     "id": 2,
     *     "name": "Conditioner",
     *     "price": 12.99,
     *     "stock": 8
     *   }
     * ]
     */
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        try {
            return ResponseEntity.ok(inventoryService.getAllProducts());
        } catch (HttpClientErrorException.NotFound e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (DataAccessException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Retrieves a specific product by its unique identifier.
     *
     * This endpoint fetches a single product from the inventory system using the provided ID.
     * Returns the product details if found, or a 404 status if the product does not exist.
     * Includes comprehensive logging for request tracking and debugging purposes.
     *
     * @param id the unique identifier of the product to retrieve
     * @return ResponseEntity containing the product data with HTTP 200 status on success,
     *         or HTTP 404 status if no product is found with the specified ID
     * @throws HttpClientErrorException.NotFound if the product with the given ID does not exist
     *
     * @apiNote The ID must be a valid positive long value. The endpoint performs exact match
     *          lookup and returns a single product entity.
     *
     * @example
     * // Sample request: GET /api/inventory/product/findById/123
     *
     * // Sample response (200 OK):
     * {
     *   "id": 123,
     *   "name": "Professional Shampoo",
     *   "description": "Premium hair care product",
     *   "salePrice": 25.99,
     *   "costPrice": 15.50,
     *   "currentStock": 45,
     *   "minimumStock": 10,
     *   "createdAt": "2023-10-05T10:30:00Z",
     *   "updatedAt": "2023-10-05T14:20:00Z"
     * }
     *
     * @see InventoryService#getProductById(Long)
     * @since 1.0
     */
    @GetMapping("/product/findById/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        log.info("Enter in controller getProductById");
        try {
            return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getProductById(id));
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Searches for products by name with case-insensitive matching.
     *
     * This endpoint retrieves products whose names contain the provided search string,
     * ignoring case differences. Returns a list of matching products or an empty list
     * if no matches are found. Useful for implementing product search functionality
     * in the inventory management system.
     *
     * @param name the search string to match against product names (case-insensitive)
     * @return ResponseEntity containing a list of matching products with HTTP 200 status,
     *         or HTTP 404 status if an error occurs during the search process
     * @throws HttpClientErrorException.NotFound if the search operation fails to retrieve results
     *
     * @apiNote The search performs partial matching, so products containing the
     *          search string anywhere in their name will be returned. The search
     *          is case-insensitive for better user experience.
     *
     * @example
     * // Sample request: GET /api/inventory/product/shampoo
     *
     * // Sample response (200 OK):
     * [
     *   {
     *     "id": 1,
     *     "name": "Professional Shampoo",
     *     "description": "Premium hair care product",
     *     "salePrice": 25.99,
     *     "currentStock": 45
     *   },
     *   {
     *     "id": 2,
     *     "name": "Anti-Dandruff Shampoo",
     *     "description": "Specialized dandruff treatment",
     *     "salePrice": 19.99,
     *     "currentStock": 22
     *   }
     * ]
     *
     * @see InventoryService#findByNameIgnoreCase(String)
     * @since 1.0
     */
    @GetMapping("/product/{name}")
    public ResponseEntity<?> findByNameIgnoreCase(@PathVariable String name) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(inventoryService.findByNameIgnoreCase(name));
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Searches for products by name using case-insensitive partial matching.
     *
     * This endpoint retrieves products whose names contain the provided search string,
     * ignoring case differences. Returns a list of matching products or an empty list
     * if no matches are found. Useful for implementing flexible product search
     * functionality in the inventory management system.
     *
     * @param name the search string to match against product names (case-insensitive)
     * @return ResponseEntity containing a list of matching products with HTTP 200 status,
     *         or HTTP 404 status if the search operation fails
     * @throws HttpClientErrorException.NotFound if the search operation cannot be completed
     *
     * @apiNote The search performs partial matching, so products containing the search
     *          string anywhere in their name will be returned. The search is case-insensitive
     *          for better user experience. Returns empty list rather than 404 when no
     *          products are found to distinguish between "no results" and "service error".
     *
     * @example
     * // Sample request: GET /api/inventory/product/find/shampoo
     *
     * // Sample response (200 OK):
     * [
     *   {
     *     "id": 1,
     *     "name": "Professional Shampoo",
     *     "description": "Premium hair care product",
     *     "salePrice": 25.99,
     *     "currentStock": 45
     *   },
     *   {
     *     "id": 2,
     *     "name": "Anti-Dandruff Shampoo",
     *     "description": "Specialized dandruff treatment",
     *     "salePrice": 19.99,
     *     "currentStock": 22
     *   }
     * ]
     *
     * @see InventoryService#findByNameContainingIgnoreCase(String)
     * @since 1.0
     */
    @GetMapping("/product/find/{name}")
    public ResponseEntity<?> findByNameContainingIgnoreCase(@PathVariable String name) {
        try {
            return ResponseEntity.ok(inventoryService.findByNameContainingIgnoreCase(name));
        } catch (HttpClientErrorException.NotFound e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // --- 2. GESTIÓN DE STOCK (MOVIMIENTOS) ---

    /**
     * Registers a stock entry for a specific product by increasing its current stock.
     *
     * This endpoint processes inventory restocking operations by adding the specified
     * quantity to the product's current stock level. It handles both successful updates
     * and various error scenarios with appropriate HTTP status codes and logging.
     *
     * @param id the unique identifier of the product to update
     * @param quantity the quantity of stock to add to the product (must be positive)
     * @return ResponseEntity containing the operation result with HTTP 200 status on success,
     *         HTTP 404 if the product is not found, or HTTP 500 for internal server errors
     * @throws HttpClientErrorException.NotFound if the specified product does not exist
     * @throws HttpServerErrorException.InternalServerError for unexpected server errors
     *
     * @apiNote This endpoint uses PUT method as it updates an existing resource (product stock).
     *          The quantity must be a positive integer. Use this for supplier deliveries,
     *          purchase orders, and inventory adjustments that increase stock levels.
     *
     * @example
     * // Sample request:
     * PUT /api/inventory/stock/entry/123?quantity=50
     *
     * // Sample response (200 OK):
     * true
     *
     * // Sample response (404 Not Found):
     * // Empty body
     *
     * @see InventoryService#registerStockEntry(Long, Integer)
     * @since 1.0
     */
    @PutMapping("/stock/entry/{id}")
    public ResponseEntity<?> registerStockEntry(@PathVariable Long id, @RequestParam Integer quantity) {
        log.info("Processing stock entry - Product ID: {}, Quantity: {}", id, quantity);

        try {
            return ResponseEntity.status(HttpStatus.OK).body(inventoryService.registerStockEntry(id, quantity));
        } catch (HttpClientErrorException.NotFound clientErrorException) {
            log.error(clientErrorException.getMessage());
            return ResponseEntity.notFound().build();
        } catch (HttpServerErrorException.InternalServerError internalServerError) {
            return ResponseEntity.internalServerError().body(internalServerError);
        }
    }

    /**
     * Registers an output stock adjustment for a specific product by decreasing its current stock.
     *
     * This endpoint processes inventory reduction operations such as sales, waste, damage,
     * or manual adjustments by subtracting the specified quantity from the product's current
     * stock level. Includes authorization validation for restricted operations.
     *
     * @param idProduct the unique identifier of the product to adjust stock for
     * @param quantity the quantity of stock to subtract from the product (must be positive)
     * @return ResponseEntity containing the operation result with HTTP 200 status on success,
     *         or HTTP 500 for internal server errors
     * @throws HttpServerErrorException.InternalServerError for unexpected server errors
     *         or authorization failures
     *
     * @apiNote This endpoint typically requires ADMIN privileges for manual stock adjustments.
     *          The quantity must be a positive integer representing the amount to subtract.
     *          Use this for sales transactions, inventory write-offs, damaged goods,
     *          and manual stock reductions.
     *
     * @example
     * // Sample request (Admin required):
     * PUT /api/inventory/stock/adjustment/output/123?quantity=5
     *
     * // Sample response (200 OK):
     * true
     *
     * // Sample response (500 Internal Server Error):
     * "Insufficient stock available"
     *
     * @see InventoryService#registerOutputStock(Long, Integer)
     * @since 1.0
     */

    @PutMapping("/stock/adjustment/output/{idProduct}")
    public ResponseEntity<?> registerOutputStock(@PathVariable Long idProduct, @RequestParam Integer quantity) {
        // Lógica de validación (ej. solo ADMIN puede hacer ajustes manuales)
        try {
            return ResponseEntity.status(HttpStatus.OK).body(inventoryService.registerOutputStock(idProduct, quantity));
        } catch (HttpServerErrorException.InternalServerError e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    // --- 3. REPORTES Y ALERTAS ---

    //OK
    @GetMapping("/alert/low-stock")
    // Este método requeriría autenticación de un rol con permisos (ej. ADMIN o EMPLEADO)
    public ResponseEntity<List<ProductResponse>> findAllByStockActualLessThanEqual() {
        List<Product> productList = inventoryService.findAllByCurrentStockLessThanEqual(10);

        try {
            if (productList.isEmpty()) {
                return ResponseEntity.ok().build();
            }
            return null;
        } catch (Exception e) {
            List<ProductResponse> productResponseList = new ArrayList<>();

            for (Product response : productList) {
                productResponseList.add(productMapper.toProductResponse(response));
            }
            return ResponseEntity.ok(productResponseList);

        }
    }

    // OK
    @GetMapping("/reporte/costo-total")
    public ResponseEntity<String> getTotalInventoryCost() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getTotalInventoryCost());
        } catch (HttpServerErrorException.InternalServerError e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
