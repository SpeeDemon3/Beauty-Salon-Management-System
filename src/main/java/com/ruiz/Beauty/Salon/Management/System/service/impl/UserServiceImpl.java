package com.ruiz.Beauty.Salon.Management.System.service.impl;

import com.ruiz.Beauty.Salon.Management.System.controller.dto.EmployeeRequest;
import com.ruiz.Beauty.Salon.Management.System.controller.dto.EmployeeResponse;
import com.ruiz.Beauty.Salon.Management.System.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Override
    public EmployeeResponse registerNewEmployee(EmployeeRequest employee) {
        /**
         * Crea una cuenta de empleado.
         *
         * Utiliza un PasswordEncoder (BCrypt) para hashear la contraseña. Asigna el rol por defecto.
         */
        return null;
    }

    @Override
    public void loadUserByUsername(String email) {

        /**
         * Implementación de la interfaz UserDetailsService de Spring Security.
         *
         * Core de la autenticación. Busca el empleado/usuario por email para la validación de credenciales.
         */

    }

    @Override
    public EmployeeResponse updateEmployeeProfile(Long id, EmployeeRequest data) {
        /**
         * Modifica datos del empleado (ej. horario, comisión).
         *
         * Implementa lógica de autorización: solo el ADMIN o el mismo empleado pueden modificar ciertos campos.
         */
        return null;
    }
}
