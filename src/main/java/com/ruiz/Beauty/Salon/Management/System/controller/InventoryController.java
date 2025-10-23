package com.ruiz.Beauty.Salon.Management.System.controller;

import com.ruiz.Beauty.Salon.Management.System.controller.dto.ProductRequest;
import com.ruiz.Beauty.Salon.Management.System.controller.dto.ProductResponse;
import com.ruiz.Beauty.Salon.Management.System.controller.mapper.ProductMapper;
import com.ruiz.Beauty.Salon.Management.System.model.Product;
import com.ruiz.Beauty.Salon.Management.System.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    //OK
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productData) {
        try {
            return ResponseEntity.ok(inventoryService.updateProduct(id, productData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    //OK
    @GetMapping("/products")
    public ResponseEntity<List<?>> getAllProducts() {
        try {
            return ResponseEntity.ok(inventoryService.getAllProducts());
        } catch (HttpClientErrorException.NotFound e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    //OK
    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getProductById(id));
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.notFound().build();
        }
    }

    //OK
    @GetMapping("/product/{name}")
    public ResponseEntity<?> findByNameIgnoreCase(@PathVariable String name) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(inventoryService.findByNameIgnoreCase(name));
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product/find/{name}")
    public ResponseEntity findByNameContainingIgnoreCase(@PathVariable String name) {
        try {
            return ResponseEntity.ok(inventoryService.findByNameContainingIgnoreCase(name));
        } catch (HttpClientErrorException.NotFound e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // --- 2. GESTIÓN DE STOCK (MOVIMIENTOS) ---

    //OK
    @PostMapping("/stock/entry/{id}")
    public ResponseEntity<?> registerStockEntry(@PathVariable Long id, @RequestParam Integer amount) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(inventoryService.registerStockEntry(id, amount));
        } catch (HttpClientErrorException.NotFound clientErrorException) {
            return ResponseEntity.notFound().build();
        } catch (HttpServerErrorException.InternalServerError internalServerError) {
            return ResponseEntity.internalServerError().body(internalServerError);
        }
    }

    //OK
    // Nota: La salida de stock se maneja principalmente dentro de TransaccionService,
    // pero se podría exponer un endpoint para ajustes manuales.
    @PostMapping("/stock/adjustment/output/{idProduct}")
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
