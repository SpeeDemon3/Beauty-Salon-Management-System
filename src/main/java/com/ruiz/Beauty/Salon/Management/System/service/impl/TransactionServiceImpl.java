package com.ruiz.Beauty.Salon.Management.System.service.impl;

import com.ruiz.Beauty.Salon.Management.System.controller.dto.TransactionRequest;
import com.ruiz.Beauty.Salon.Management.System.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {
    @Override
    public void finalSale(TransactionRequest request) {
        /**
         * Procesa y finaliza una venta.
         *
         * Inicia una transacción de base de datos (@Transactional). Crea la Transaccion (cabecera).
         * Crea múltiples ItemTransaccion (detalles). Llama a InventarioService para deducir el stock.
         */

    }

    @Override
    public void calculateEmployeeCommission(Long employeeId, LocalDate start, LocalDate end) {
        /**
         * Genera un reporte de comisiones ganadas por un empleado en un rango de fechas.
         */
    }

    @Override
    public void getNetIncome(LocalDate start, LocalDate end) {
        /**
         * Suma los ingresos totales por ventas en un periodo.
         */
    }
}
