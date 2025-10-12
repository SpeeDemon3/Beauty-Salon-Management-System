package com.ruiz.Beauty.Salon.Management.System.controller.dto;

import com.ruiz.Beauty.Salon.Management.System.enums.AppointmentState;
import com.ruiz.Beauty.Salon.Management.System.model.Client;
import com.ruiz.Beauty.Salon.Management.System.model.Employee;
import com.ruiz.Beauty.Salon.Management.System.model.Services;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AppointmentResponse {

    private Long id;
    private ClientResponse client;
    private EmployeeResponse employee;
    private ServicesResponse service;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private AppointmentState state;

}
