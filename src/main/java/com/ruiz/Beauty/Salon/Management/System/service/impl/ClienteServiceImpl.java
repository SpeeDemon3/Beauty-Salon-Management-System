package com.ruiz.Beauty.Salon.Management.System.service.impl;

import com.ruiz.Beauty.Salon.Management.System.controller.dto.ClientRequest;
import com.ruiz.Beauty.Salon.Management.System.controller.dto.ClientResponse;
import com.ruiz.Beauty.Salon.Management.System.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class ClienteServiceImpl implements ClienteService {
    @Override
    public ClientResponse createOrUpdateClient(ClientRequest client) {
        /**
         * Registra o actualiza la ficha de un cliente.
         *
         * Verifica si el cliente ya existe por email o teléfono antes de crear uno nuevo.
         */
        return null;
    }

    @Override
    public void applyLoyaltyPoints(Long customerId, BigDecimal amountSpent) {
        /**
         * Suma puntos al cliente después de una transacción.
         *
         * Lógica de negocio para fidelización (ej. €10 gastados = 1 punto).
         */
    }

    @Override
    public void getClientHistory(Long clientId) {
        /**
         * Muestra todas las Citas y Transacciones asociadas a ese cliente.
         *
         * Utiliza joins o múltiples llamadas a repositorios para agregar datos relacionados.
         */
    }
}
