package com.ruiz.Beauty.Salon.Management.System.service;

import com.ruiz.Beauty.Salon.Management.System.controller.dto.AppointmentRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface AppointmentService {
    AppointmentResponse reserveAppointment(AppointmentRequest appointmentData);
    void cancelAppointment(Long appointmentId, Long clientId);
    void getAvailability(Long employeeId, LocalDate date);
    void getAppointmentsByDate(LocalDate date);
    void confirmAppointment(Long appointmentId);
}
