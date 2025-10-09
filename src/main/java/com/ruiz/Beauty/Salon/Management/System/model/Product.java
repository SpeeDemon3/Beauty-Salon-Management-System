package com.ruiz.Beauty.Salon.Management.System.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entidad que representa un producto en el sistema de gestión del salón de belleza.
 * Incluye detalles como nombre, descripción, precio y stock.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {

    /**
     * Atributo (Java)	Tipo de Dato	Rol / Descripción
     * id	Long	Clave primaria (PK).
     * nombre	String	Nombre del producto.
     * descripcion	String	Breve descripción.
     * precioVenta	BigDecimal	Precio al que se vende al cliente.
     * precioCosto	BigDecimal	Precio pagado al proveedor (para cálculo de ganancias).
     * stockActual	Integer	Cantidad disponible en inventario.
     * stockMinimo	Integer	Nivel para generar alerta de bajo stock.
     */

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private BigDecimal salePrice;
    private BigDecimal costPrice;
    private Integer currentStock;
    private Integer minimumStock;
}
