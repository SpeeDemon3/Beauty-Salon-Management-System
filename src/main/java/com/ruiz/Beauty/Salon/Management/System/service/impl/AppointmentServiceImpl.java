package com.ruiz.Beauty.Salon.Management.System.service.impl;

import com.ruiz.Beauty.Salon.Management.System.controller.dto.AppointmentRequest;
import com.ruiz.Beauty.Salon.Management.System.controller.dto.AppointmentResponse;
import com.ruiz.Beauty.Salon.Management.System.service.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class AppointmentServiceImpl implements AppointmentService {


    @Override
    public AppointmentResponse reserveAppointment(AppointmentRequest appointmentData) {
        /**
         * Crea una nueva cita en el sistema.
         *
         * Verifica la disponibilidad del empleado y si el horario está abierto. Persiste la cita.
         */
        return null;
    }

    @Override
    public Boolean cancelAppointment(Long appointmentId, Long clientId) {
        /**
         * Marca una cita como CANCELADA.
         *
         * Verifica que el cliente o administrador tenga permiso para cancelar. Envía notificación (simulada).
         */
        return null;
    }

    @Override
    public void getAvailability(Long employeeId, LocalDate date) {
        /**
         * Devuelve los slots de tiempo disponibles para un empleado y día.
         *
         * Lógica de cálculo de tiempo: Resta la duración de las citas existentes al horario laboral.
         */
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByDate(LocalDate date) {
        /**
         * Lista todas las citas para un día específico.
         *
         * Utiliza consultas al CitaRepository con criterios de fecha.
         */
        return List.of();
    }

    @Override
    public Boolean confirmAppointment(Long appointmentId) {
        /**
         * Cambia el estado de una cita a CONFIRMADA.
         *
         * Cambia el estado de la cita a CONFIRMADA si está en estado PENDIENTE.
         */
        return null;
    }
}
