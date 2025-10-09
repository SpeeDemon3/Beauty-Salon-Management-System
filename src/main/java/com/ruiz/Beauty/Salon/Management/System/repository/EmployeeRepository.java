package com.ruiz.Beauty.Salon.Management.System.repository;

import com.ruiz.Beauty.Salon.Management.System.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Employee.
 * Proporciona métodos CRUD y de consulta para gestionar empleados en el sistema.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
