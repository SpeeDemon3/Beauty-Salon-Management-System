package com.ruiz.Beauty.Salon.Management.System.service.impl;

import com.ruiz.Beauty.Salon.Management.System.controller.dto.ClientRequest;
import com.ruiz.Beauty.Salon.Management.System.controller.dto.ClientResponse;
import com.ruiz.Beauty.Salon.Management.System.model.Client;
import com.ruiz.Beauty.Salon.Management.System.repository.ClientRepository;
import com.ruiz.Beauty.Salon.Management.System.service.ClienteService;
import com.ruiz.Beauty.Salon.Management.System.service.converter.ClientConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientConverter clientConverter;

    @Override
    public ClientResponse createOrUpdateClient(ClientRequest client) {
        /**
         * Registra o actualiza la ficha de un cliente.
         *
         * Verifica si el cliente ya existe por email o teléfono antes de crear uno nuevo.
         */


        if(client.getEmail() != null) {

            Optional<Client> optionalClient = clientRepository.findByEmail(client.getEmail());

            if (optionalClient.isPresent()) {

                Client existingClient = optionalClient.get();

                if (client.getName() != null) {
                    existingClient.setName(client.getName());
                }

                if (client.getEmail() != null) {
                    existingClient.setEmail(client.getEmail());
                }

                if (client.getPhone() != null) {
                    existingClient.setPhone(client.getPhone());
                }

                if (client.getNotes() != null) {
                    existingClient.setNotes(client.getNotes());
                }

                if (client.getLoyaltyPoints() != null) {
                    existingClient.setLoyaltyPoints(client.getLoyaltyPoints());
                }

                if (client.getRegistrationDate() != null) {
                    existingClient.setRegistrationDate(client.getRegistrationDate());
                }

                Client clientSave = clientRepository.save(existingClient);

                return clientConverter.toClientRespone(clientSave);

            } else {

                Client clientSave = clientRepository.save(clientConverter.toClient(client));

                return clientConverter.toClientRespone(clientSave);

            }


        }

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
