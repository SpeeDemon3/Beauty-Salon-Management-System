package com.ruiz.Beauty.Salon.Management.System.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa un servicio ofrecido por el salón de belleza.
 * Incluye detalles como nombre, descripción, precio y duración.
 */

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "services")
public class Services {
    /**
     * id	Long	Clave primaria (PK).
     * nombre	String	Nombre del servicio (ej. "Corte de Dama", "Tinte Completo").
     * descripcion	String	Detalle del servicio.
     * precio	BigDecimal	Costo del servicio.
     * duracionMinutos	Integer	Tiempo estimado que toma el servicio (para calcular la disponibilidad).
     */

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String price;
    private Integer durationMinutes;

}
