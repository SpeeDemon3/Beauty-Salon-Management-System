package com.ruiz.Beauty.Salon.Management.System.model;

import com.ruiz.Beauty.Salon.Management.System.enums.ROL;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad que representa a un empleado en el sistema de gestión del salón de belleza.
 * Incluye detalles personales, rol, y condiciones laborales.
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {

    /*
    Atributo (Java)	Tipo de Dato	Rol / Descripción
    id	Long	Clave primaria (PK). Identificador único.
    nombre	String	Nombre completo del empleado.
    email	String	Correo para notificaciones y login (debe ser único).
    password	String	Contraseña hasheada (usada con Spring Security).
    rol	String / Enum	Rol dentro del sistema (ADMIN, EMPLEADO).
    telefono	String	Número de contacto.
    horarioDisponible	Colección de TimeSlot	Días y horas en las que el empleado trabaja.
    fechaContratacion	LocalDate	Fecha de inicio en la empresa.
    comisionRate	BigDecimal	Porcentaje de comisión por servicio/venta.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private ROL rol;

    private String phone;
    private LocalDate hiringDate;
    private BigDecimal commissionRate;




}
