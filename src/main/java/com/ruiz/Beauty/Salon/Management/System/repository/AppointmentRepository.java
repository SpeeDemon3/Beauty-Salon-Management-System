package com.ruiz.Beauty.Salon.Management.System.repository;

import com.ruiz.Beauty.Salon.Management.System.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Appointment.
 * Proporciona métodos CRUD y consultas personalizadas para gestionar citas en el sistema de gestión del salón de belleza.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
