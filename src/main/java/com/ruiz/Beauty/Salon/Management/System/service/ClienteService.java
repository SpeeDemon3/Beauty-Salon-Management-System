package com.ruiz.Beauty.Salon.Management.System.service;

import com.ruiz.Beauty.Salon.Management.System.controller.dto.ClientRequest;
import com.ruiz.Beauty.Salon.Management.System.controller.dto.ClientResponse;
import com.ruiz.Beauty.Salon.Management.System.model.Client;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface ClienteService {
    ClientResponse createOrUpdateClient(ClientRequest client);
    void applyLoyaltyPoints(Long customerId, BigDecimal amountSpent);
    void getClientHistory(Long clientId);

}
