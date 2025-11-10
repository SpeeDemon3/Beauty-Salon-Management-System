package com.ruiz.Beauty.Salon.Management.System.exceptions;
/**
 * Excepción personalizada para errores de autenticación.
 */
public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }
}
