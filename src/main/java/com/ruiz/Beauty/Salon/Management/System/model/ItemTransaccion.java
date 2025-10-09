package com.ruiz.Beauty.Salon.Management.System.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entidad que representa un ítem dentro de una transacción (venta) en el sistema de gestión del salón de belleza.
 * Cada ítem puede ser un producto o un servicio vendido.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "itemTransaccion")
public class ItemTransaccion {

    /**
     *
     id	Long	Clave primaria (PK).
     transaccion	Transaccion	Relación Many-to-One (FK). A qué venta pertenece este ítem.
     producto	Producto	Relación Many-to-One (FK). El producto vendido. (Puede ser null si es un servicio).
     servicio	Servicio	Relación Many-to-One (FK). El servicio prestado. (Puede ser null si es un producto).
     cantidad	Integer	Cuántas unidades del producto/servicio se vendieron.
     precioUnitario	BigDecimal	El precio del artículo en el momento exacto de la venta. Importante: Esto evita problemas si el precio del Producto o Servicio cambia más tarde.
     subtotal	BigDecimal	Calculado como cantidad * precioUnitario.
     */

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaccion transaction;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Services service;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

}
