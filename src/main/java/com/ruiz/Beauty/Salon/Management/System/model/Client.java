package com.ruiz.Beauty.Salon.Management.System.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

/**
 * Entidad que representa a un cliente en el sistema de gestión del salón de belleza.
 * Incluye detalles personales, información de contacto, historial y preferencias.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "client")
public class Client {

    /*
        Atributo (Java)	Tipo de Dato	Rol / Descripción
        id	Long	Clave primaria (PK).
        nombre	String	Nombre completo.
        email	String	Correo para notificaciones y marketing.
        telefono	String	Contacto principal.
        fechaRegistro	LocalDate	Fecha en la que se registró el cliente.
        notas	String	Notas del estilista (ej. alergias, tipo de cabello, preferencias).
        puntosFidelidad	Integer	Puntos acumulados en el programa de fidelización.

     */

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String notes; // Notas del estilista (ej. alergias, tipo de cabello, preferencias).
    private Integer loyaltyPoints;
    private LocalDate registrationDate;


}
