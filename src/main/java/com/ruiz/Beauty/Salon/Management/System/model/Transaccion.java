package com.ruiz.Beauty.Salon.Management.System.model;

import com.ruiz.Beauty.Salon.Management.System.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Entidad que representa una transacción o venta en el sistema de gestión del salón de belleza.
 * Incluye detalles como empleado, cliente, fecha, monto total, método de pago e ítems vendidos.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaccion {

    /**
     *
     id	Long	Clave primaria (PK).
     empleado	Empleado	Relación Many-to-One (FK). El empleado que registró la venta.
     cliente	Cliente	Relación Many-to-One (Opcional FK). El cliente que pagó.
     fechaTransaccion	LocalDateTime	Momento exacto de la venta.
     montoTotal	BigDecimal	Suma de todos los ítems vendidos.
     metodoPago	String / Enum	(EFECTIVO, TARJETA, TRANSFERENCIA).
     itemsVendidos	Colección de ItemTransaccion	Relación One-to-Many (FK). Detalle de lo que se vendió.
     */

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    private String transactionDate;
    private String totalAmount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<ItemTransaccion> soldItems;
}
