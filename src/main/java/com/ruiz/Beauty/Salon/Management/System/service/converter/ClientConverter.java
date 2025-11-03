package com.ruiz.Beauty.Salon.Management.System.service.converter;

import com.ruiz.Beauty.Salon.Management.System.controller.dto.ClientRequest;
import com.ruiz.Beauty.Salon.Management.System.controller.dto.ClientResponse;
import com.ruiz.Beauty.Salon.Management.System.model.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientConverter {

    public Client toClient(ClientRequest request) {
        Client clientEntity = Client.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .notes(request.getNotes())
                .loyaltyPoints(request.getLoyaltyPoints())
                .registrationDate(request.getRegistrationDate())
                .build();

        return clientEntity;
    }

    public ClientResponse toClientRespone(ClientResponse response) {
        ClientResponse clientResponse = ClientResponse.builder()
                .id(response.getId())
                .name(response.getName())
                .email(response.getEmail())
                .phone(response.getPhone())
                .notes(response.getNotes())
                .loyaltyPoints(response.getLoyaltyPoints())
                .registrationDate(response.getRegistrationDate())
                .build();

        return clientResponse;
    }

}
