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

    public ClientResponse toClientRespone(Client clientEntity) {
        ClientResponse clientResponse = ClientResponse.builder()
                .id(clientEntity.getId())
                .name(clientEntity.getName())
                .email(clientEntity.getEmail())
                .phone(clientEntity.getPhone())
                .notes(clientEntity.getNotes())
                .loyaltyPoints(clientEntity.getLoyaltyPoints())
                .registrationDate(clientEntity.getRegistrationDate())
                .build();

        return clientResponse;
    }

}
