package com.ruiz.Beauty.Salon.Management.System.controller;

import com.ruiz.Beauty.Salon.Management.System.exceptions.AuthenticationException;
import com.ruiz.Beauty.Salon.Management.System.model.LoginRequest;
import com.ruiz.Beauty.Salon.Management.System.model.LoginResponse;
import com.ruiz.Beauty.Salon.Management.System.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    /**
     * Endpoint (URL Base: /api/auth)	Método HTTP	Propósito	Servicio Invocado
     * /login	POST	Procesa las credenciales y devuelve un token JWT (si lo implementas) o inicia la sesión.	AuthService
     * /register	POST	Permite registrar nuevos empleados (solo para el rol ADMIN).	AuthService
     * /password-reset	POST	Inicia el proceso de restablecimiento de contraseña.	AuthService
     *
     * Exportar a Hojas de cálculo
     */
    private final AuthenticationService authenticationService;

    /**
     * Endpoint para login de empleados.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        log.info("Login request received for employee: {}", request.getEmail());

        try {
            LoginResponse response = authenticationService.authenticate(request);
            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            log.warn("Login failed for employee: {} - {}", request.getEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Authentication failed", "message", e.getMessage()));
        }
    }

    /**
     * Endpoint para verificar token (opcional).
     */
    @PostMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid authorization header"));
        }

        String token = authHeader.substring(7);
        // La validación real se hace en el filtro JWT
        return ResponseEntity.ok(Map.of("valid", true));
    }
}
