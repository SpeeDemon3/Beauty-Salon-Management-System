package com.ruiz.Beauty.Salon.Management.System.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {
    /**
     * Endpoint (URL Base: /api/citas)	Método HTTP	Propósito	Servicio Invocado
     * /	POST	Crea una nueva cita/reserva.	CitaService.reservarCita()
     * /{id}	DELETE	Cancela una cita específica por ID.	CitaService.cancelarCita()
     * /disponibilidad	GET	Consulta los horarios disponibles para un empleado y una fecha dados.	CitaService.obtenerDisponibilidad()
     * /fecha/{fecha}	GET	Obtiene todas las citas para un día específico (para el panel de administración).	CitaService.obtenerCitasPorFecha()
     *
     * Exportar a Hojas de cálculo
     */
}
