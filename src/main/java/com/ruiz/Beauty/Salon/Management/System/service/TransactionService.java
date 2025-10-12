package com.ruiz.Beauty.Salon.Management.System.service;

import com.ruiz.Beauty.Salon.Management.System.controller.dto.TransactionRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface TransactionService {

    void finalSale(TransactionRequest request);
    void calculateEmployeeCommission(Long employeeId, LocalDate start, LocalDate end);
    void getNetIncome(LocalDate start, LocalDate end);

}
