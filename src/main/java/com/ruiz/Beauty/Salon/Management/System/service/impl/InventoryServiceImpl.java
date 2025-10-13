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

    @Override
    public Boolean registerOutputStock(Long productId, Integer quantity) {
        /**
         * Registra una deducción de stock por venta o uso en un servicio. Disminuye el stock actual.
         * Validación Crítica: Debe llamar a verificarStockSuficiente() antes de la deducción.
         * Lanza una excepción si no hay suficiente.
         */
        return null;
    }

    @Override
    public Boolean checkSufficientStock(Long productId, Integer quantityRequired) {
        /**
         * Método auxiliar privado que verifica si el stockActual de un producto cubre la cantidad solicitada.
         * Devuelve true/false. Usado internamente por registrarSalidaStock.
         */
        return null;
    }

    //OK
    @Override
    public ProductResponse createProduct(ProductRequest productData) {

        Product productEntity = productConverter.toProduct(productData);
        log.info("Converted to CarEntity: {}", productData);

        Product productSave = inventoryRepository.save(productEntity);
        log.info("Saved CarEntity: {}", productSave);

        return productConverter.toProductResponse(productSave);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productData) {
        /**
         * Modifica datos como el precioVenta, precioCosto o stockMinimo.
         * Busca el producto por ID, aplica las actualizaciones y guarda.
         */
        return null;
    }

    //OK
    @Override
    public ProductResponse getProductById(Long id) {

        Optional<Product> productOptional = inventoryRepository.findById(id);

        if (productOptional.isPresent()) {
            return productConverter.toProductResponse(productOptional.get());
        }

        return null;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        /**
         * Lista todo el catálogo.	Utiliza ProductoRepository.findAll().
         */
        return List.of();
    }

    @Override
    public String generateLowStockAlert() {
        /**
         * Devuelve una lista de productos que están por debajo de su stockMinimo.
         * Llama a un método personalizado en el ProductoRepository: findAllByStockActualLessThan(stockMinimo).
         */
        return "";
    }

    @Override
    public String getTotalInventoryCost() {
        /**
         * Calcula el valor total de todos los productos en stock usando su precioCosto.
         * Itera sobre todos los productos y suma (stockActual * precioCosto).
         */
        return "";
    }

    /**
     * Busca un producto por su nombre ignorando mayúsculas/minúsculas.
     * Útil para búsquedas en el catálogo.
     */
    @Override
    public Optional<Product> findByNameIgnoreCase(String name) {
        return Optional.empty();
    }

    /**
     * Obtiene una lista de productos cuyo stock actual es menor
     * o igual a un nivel específico. Esencial para las alertas.
     *
     * @param stockLimit El umbral mínimo a comparar (normalmente el campo 'stockMinimo' de la entidad).
     * @return Lista de productos con stock bajo.
     */
    @Override
    public List<Product> findAllByStockActualLessThanEqual(Integer stockLimit) {
        return List.of();
    }

    /**
     * Busca productos cuyo nombre contenga la cadena proporcionada.
     * Útil para la función de autocompletar o búsqueda en el TPV.
     */
    @Override
    public List<Product> findByNameContainingIgnoreCase(String name) {
        return List.of();
    }
}
