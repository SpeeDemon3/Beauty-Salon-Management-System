package com.ruiz.Beauty.Salon.Management.System.repository;

import com.ruiz.Beauty.Salon.Management.System.model.ItemTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad ItemTransaccion.
 * Proporciona métodos CRUD y de consulta para los ítems de transacciones (ventas).
 */
@Repository
public interface ItemTransaccionRepository extends JpaRepository<ItemTransaccion, Long> {
}
