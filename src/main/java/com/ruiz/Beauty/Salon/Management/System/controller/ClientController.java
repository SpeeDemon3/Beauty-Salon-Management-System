package com.ruiz.Beauty.Salon.Management.System.controller;

import com.ruiz.Beauty.Salon.Management.System.controller.dto.ClientRequest;
import com.ruiz.Beauty.Salon.Management.System.controller.dto.ClientResponse;
import com.ruiz.Beauty.Salon.Management.System.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/client")
public class ClientController {
    /**
     * Endpoint (URL Base: /api/clientes)	Método HTTP	Propósito	Servicio Invocado
     * /{id}	GET	Obtiene la información detallada de un cliente y su historial.	ClienteService.obtenerHistorialCliente()
     * /{id}/puntos	PUT	Aplica o canjea puntos de fidelidad.	ClienteService.aplicarPuntosFidelidad()
     *
     */

    @Autowired
    private ClienteService clientService;

    /**
     * Creates a new client or updates an existing client by email.
     *
     * This endpoint provides upsert functionality - if a client with the provided
     * email already exists, it will be updated; otherwise, a new client will be created.
     * Supports partial updates for existing clients.
     *
     * @param clientRequest the client data to create or update
     * @return ResponseEntity containing the created/updated client with HTTP 200 status,
     *         HTTP 400 if the request is invalid, or HTTP 500 for internal errors
     * @throws IllegalArgumentException if client data validation fails
     *
     * @apiNote This endpoint uses POST for both create and update operations (upsert pattern).
     *          The email field is used as the unique identifier for client matching.
     *
     * @example
     * // Create new client:
     * POST /api/clients
     * {
     *   "name": "María García",
     *   "email": "maria@email.com",
     *   "phone": "+1234567890",
     *   "notes": "Prefiere contacto por email"
     * }
     *
     * // Update existing client (same email):
     * POST /api/clients
     * {
     *   "name": "María García López",
     *   "email": "maria@email.com",
     *   "phone": "+1234567899",
     *   "loyaltyPoints": 150
     * }
     */
    @PostMapping
    public ResponseEntity<ClientResponse> createOrUpdateClient(@RequestBody ClientRequest clientRequest) {
        log.info("Processing client create/update request for email: {}", clientRequest.getEmail());

        try {
            ClientResponse clientResponse = clientService.createOrUpdateClient(clientRequest);

            if (clientResponse != null) {
                log.info("Client operation completed successfully for email: {}", clientRequest.getEmail());
                return ResponseEntity.ok(clientResponse);
            } else {
                log.warn("Client operation failed - null response for email: {}", clientRequest.getEmail());
                return ResponseEntity.badRequest().build();
            }

        } catch (IllegalArgumentException e) {
            log.error("Invalid client data for email {}: {}", clientRequest.getEmail(), e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Internal server error during client operation for email {}: {}",
                    clientRequest.getEmail(), e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
