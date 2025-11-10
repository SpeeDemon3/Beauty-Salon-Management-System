package com.ruiz.Beauty.Salon.Management.System.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para respuestas de login exitoso.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String name;
    private String email;
    private String role;
    private LocalDateTime expiresAt;

    public LoginResponse(String token, Long id, String name, String email, String role, LocalDateTime expiresAt) {
        this.token = token;
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.expiresAt = expiresAt;
    }
}
