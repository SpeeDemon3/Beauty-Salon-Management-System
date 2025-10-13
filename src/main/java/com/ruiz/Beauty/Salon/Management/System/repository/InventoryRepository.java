package com.ruiz.Beauty.Salon.Management.System.repository;

import com.ruiz.Beauty.Salon.Management.System.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Product, Long> {

    /**
     * Busca un producto por su nombre ignorando mayúsculas/minúsculas.
     * Útil para búsquedas en el catálogo.
     */
    Optional<Product> findByNameIgnoreCase(String name);

    /**
     * Obtiene una lista de productos cuyo stock actual es menor
     * o igual a un nivel específico. Esencial para las alertas.
     *
     * @param stockLimit El umbral mínimo a comparar (normalmente el campo 'stockMinimo' de la entidad).
     * @return Lista de productos con stock bajo.
     */
    List<Product> findAllByStockActualLessThanEqual(Integer stockLimit);

    /**
     * Busca productos cuyo nombre contenga la cadena proporcionada.
     * Útil para la función de autocompletar o búsqueda en el TPV.
     */
    List<Product> findByNameContainingIgnoreCase(String name);
}
