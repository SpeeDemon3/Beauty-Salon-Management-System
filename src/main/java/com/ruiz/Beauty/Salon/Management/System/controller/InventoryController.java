package com.ruiz.Beauty.Salon.Management.System.controller;

import com.ruiz.Beauty.Salon.Management.System.controller.dto.ProductRequest;
import com.ruiz.Beauty.Salon.Management.System.controller.dto.ProductResponse;
import com.ruiz.Beauty.Salon.Management.System.controller.mapper.ProductMapper;
import com.ruiz.Beauty.Salon.Management.System.service.impl.InventoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/inventory")
public class InventoryController {
    private final InventoryServiceImpl inventarioService;

    @Autowired
    private ProductMapper productMapper;

    // Inyección de dependencias (por constructor, la mejor práctica)
    @Autowired
    public InventoryController(InventoryServiceImpl inventarioService) {
        this.inventarioService = inventarioService;
    }

    // --- 1. GESTIÓN DEL CATÁLOGO (CRUD BÁSICO) ---

    //OK
    @PostMapping("/products")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productData) {

        try {
            inventarioService.createProduct(productData);
            return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.toProductResponse(productData));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoData) {

        return ResponseEntity.ok(productoActualizado);
    }

    @GetMapping("/products")
    public ResponseEntity<List<?>> obtenerTodosProductos() {
        // Devuelve 200 OK
        return ResponseEntity.ok(inventarioService.obtenerTodosProductos());
    }

    //OK
    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(inventarioService.getProductById(id));
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- 2. GESTIÓN DE STOCK (MOVIMIENTOS) ---

    //OK
    @PostMapping("/stock/entry/{id}")
    public ResponseEntity<?> registerStockEntry(@PathVariable Long id, @RequestParam Integer amount) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(inventarioService.registerStockEntry(id, amount));
        } catch (HttpClientErrorException.NotFound clientErrorException) {
            return ResponseEntity.notFound().build();
        } catch (HttpServerErrorException.InternalServerError internalServerError) {
            return ResponseEntity.internalServerError().body(internalServerError);
        }
    }

    // Nota: La salida de stock se maneja principalmente dentro de TransaccionService,
    // pero se podría exponer un endpoint para ajustes manuales.
    @PostMapping("/stock/ajuste/salida/{id}")
    public ResponseEntity<?> registrarAjusteSalida(@PathVariable Long id, @RequestParam Integer cantidad) {
        // Lógica de validación (ej. solo ADMIN puede hacer ajustes manuales)
        // ...
        ProductoDTO producto = inventarioService.registrarSalidaStock(id, cantidad);
        return ResponseEntity.ok(producto);
    }

    // --- 3. REPORTES Y ALERTAS ---

    @GetMapping("/alertas/bajo-stock")
    // Este método requeriría autenticación de un rol con permisos (ej. ADMIN o EMPLEADO)
    public ResponseEntity<List<?>> obtenerAlertaBajoStock() {
        List<ProductoDTO> productosBajoStock = inventarioService.generarAlertaBajoStock();
        // Devuelve 200 OK. La lista vacía significa que no hay alertas.
        return ResponseEntity.ok(productosBajoStock);
    }

    @GetMapping("/reporte/costo-total")
    public ResponseEntity<BigDecimal> obtenerCostoTotalInventario() {
        BigDecimal costoTotal = inventarioService.obtenerCostoTotalInventario();
        return ResponseEntity.ok(costoTotal);
    }
}
