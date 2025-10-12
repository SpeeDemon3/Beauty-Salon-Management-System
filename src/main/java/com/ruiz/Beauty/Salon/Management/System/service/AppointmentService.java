package com.ruiz.Beauty.Salon.Management.System.service;

import com.ruiz.Beauty.Salon.Management.System.controller.dto.AppointmentRequest;
import com.ruiz.Beauty.Salon.Management.System.controller.dto.AppointmentResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface AppointmentService {
    AppointmentResponse reserveAppointment(AppointmentRequest appointmentData);
    Boolean cancelAppointment(Long appointmentId, Long clientId);
    void getAvailability(Long employeeId, LocalDate date);
    List<AppointmentResponse> getAppointmentsByDate(LocalDate date);
    Boolean confirmAppointment(Long appointmentId);
}
