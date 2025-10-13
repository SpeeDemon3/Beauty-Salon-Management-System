package com.ruiz.Beauty.Salon.Management.System.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/transaction")
public class TransactionController {
    /**
     * Endpoint (URL Base: /api/transacciones)	Método HTTP	Propósito	Servicio Invocado
     * /finalizar	POST	Procesa el cuerpo de la venta (servicios, productos, cliente) y la registra como finalizada.	TransaccionService.finalizarVenta()
     * /reporte/ingresos	GET	Genera un reporte de ingresos netos por un rango de fechas.	TransaccionService.obtenerIngresosNetos()
     * /reporte/comisiones/{empleadoId}	GET	Calcula la comisión de un empleado específico.	TransaccionService.calcularComisionEmpleado()
     *
     * Exportar a Hojas de cálculo
     */
}
