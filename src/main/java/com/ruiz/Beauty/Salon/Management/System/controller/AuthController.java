package com.ruiz.Beauty.Salon.Management.System.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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
}
