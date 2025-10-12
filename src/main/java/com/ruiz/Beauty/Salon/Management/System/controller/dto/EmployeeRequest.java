package com.ruiz.Beauty.Salon.Management.System.controller.dto;

import com.ruiz.Beauty.Salon.Management.System.enums.ROL;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeRequest {

    private String name;
    private String email;
    private String password;
    private ROL rol = ROL.EMPLOYEE; // Valor por defecto
    private String phone;
}
