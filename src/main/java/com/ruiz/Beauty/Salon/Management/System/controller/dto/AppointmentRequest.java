package com.ruiz.Beauty.Salon.Management.System.controller.dto;

import com.ruiz.Beauty.Salon.Management.System.enums.AppointmentState;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AppointmentRequest {

    private Long clientId;
    private Long employeeId;
    private Long serviceId;
    private String startDate;
    private AppointmentState state = AppointmentState.PENDIENTE;

}
