package com.ruiz.Beauty.Salon.Management.System.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClientRequest {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String notes; // Notas del estilista (ej. alergias, tipo de cabello, preferencias).
    private Integer loyaltyPoints;
    private LocalDate registrationDate;

}
