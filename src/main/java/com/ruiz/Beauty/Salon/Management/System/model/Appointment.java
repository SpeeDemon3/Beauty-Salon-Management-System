package com.ruiz.Beauty.Salon.Management.System.model;

import com.ruiz.Beauty.Salon.Management.System.enums.AppointmentState;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entidad que representa una cita en el sistema de gestión del salón de belleza.
 * Incluye detalles como cliente, empleado, servicio, fecha y estado.
 */

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "appointment")
public class Appointment {

    /**
     * Atributo (Java)	Tipo de Dato	Relación / Descripción
     * id	Long	Clave primaria (PK).
     * cliente	Cliente	Relación Many-to-One (FK). Quién realiza la reserva.
     * empleado	Empleado	Relación Many-to-One (FK). Quién realizará el servicio.
     * servicio	Servicio	Relación Many-to-One (FK). El servicio reservado.
     * fechaInicio	LocalDateTime	Fecha y hora exactas de inicio de la cita.
     * fechaFin	LocalDateTime	Fecha y hora calculada de fin de la cita.
     * estado	String / Enum	Estado de la cita (PENDIENTE, CONFIRMADA, CANCELADA, COMPLETADA).
     */

    private Long id;
    private Client client;
    private Employee employee;
    private Services service;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Enumerated(EnumType.STRING)
    private AppointmentState state;


}
