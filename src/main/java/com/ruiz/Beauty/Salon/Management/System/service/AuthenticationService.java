package com.ruiz.Beauty.Salon.Management.System.service;

import com.ruiz.Beauty.Salon.Management.System.model.Employee;
import com.ruiz.Beauty.Salon.Management.System.model.LoginRequest;
import com.ruiz.Beauty.Salon.Management.System.model.LoginResponse;
import com.ruiz.Beauty.Salon.Management.System.exceptions.AuthenticationException;
import com.ruiz.Beauty.Salon.Management.System.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Servicio para manejar la autenticación de empleados.
 */
@Service
@Transactional
@Slf4j
public class AuthenticationService {
    private final EmployeeRepository employeeRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(EmployeeRepository employeeRepository,
                                 AuthenticationManager authenticationManager,
                                 JwtService jwtService,
                                 PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Autentica a un empleado y genera un token JWT.
     *
     * @param request solicitud de login con email y password
     * @return LoginResponse con token y información del empleado
     * @throws AuthenticationException si las credenciales son inválidas
     */
    public LoginResponse authenticate(LoginRequest request) {
        log.info("Attempting authentication for employee: {}", request.getEmail());

        try {
            // Autenticar usando Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // Obtener el empleado autenticado
            Employee employee = (Employee) authentication.getPrincipal();

            // Generar token JWT
            String jwtToken = jwtService.generateToken(employee);
            LocalDateTime expiresAt = jwtService.extractExpiration(jwtToken);

            log.info("Authentication successful for employee: {}", employee.getEmail());

            return LoginResponse.builder()
                    .token(jwtToken)
                    .id(employee.getId())
                    .name(employee.getName())
                    .email(employee.getEmail())
                    .role(String.valueOf(employee.getRol()))
                    .expiresAt(expiresAt)
                    .build();

        } catch (BadCredentialsException e) {
            log.warn("Authentication failed for employee: {} - Invalid credentials", request.getEmail());
            throw new AuthenticationException("Invalid email or password");
        } catch (DisabledException e) {
            log.warn("Authentication failed for employee: {} - Account disabled", request.getEmail());
            throw new AuthenticationException("Account is disabled");
        } catch (Exception e) {
            log.error("Authentication error for employee: {} - {}", request.getEmail(), e.getMessage());
            throw new AuthenticationException("Authentication failed");
        }
    }

    /**
     * Registra un nuevo empleado (solo para administradores).
     */
    @Transactional
    public Employee registerEmployee(Employee employee) {
        log.info("Registering new employee: {}", employee.getEmail());

        // Verificar si el email ya existe
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new IllegalArgumentException("Email already registered: " + employee.getEmail());
        }

        // Codificar password
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee registered successfully: {}", savedEmployee.getEmail());

        return savedEmployee;
    }
}


