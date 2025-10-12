package com.ruiz.Beauty.Salon.Management.System.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionRequest {
    private Long employeeId;
    private Long clientId; // Opcional
    private String totalAmount;
    private String paymentMethod; // EFECTIVO, TARJETA, TRANSFERENCIA
    private List<ItemTransactionRequest> soldItems; // Detalle de lo que se vendió
}
