package com.ruiz.Beauty.Salon.Management.System.service;

import com.ruiz.Beauty.Salon.Management.System.controller.dto.EmployeeRequest;
import com.ruiz.Beauty.Salon.Management.System.controller.dto.EmployeeResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    EmployeeResponse registerNewEmployee(EmployeeRequest employee);
    void loadUserByUsername(String email);
    EmployeeResponse updateEmployeeProfile(Long id, EmployeeRequest data);
}
