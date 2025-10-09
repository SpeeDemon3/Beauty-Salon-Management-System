package com.ruiz.Beauty.Salon.Management.System.repository;

import com.ruiz.Beauty.Salon.Management.System.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Transaccion entities.
 */
@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
}
