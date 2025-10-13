package com.ruiz.Beauty.Salon.Management.System.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/client")
public class ClientController {
    /**
     * Endpoint (URL Base: /api/clientes)	Método HTTP	Propósito	Servicio Invocado
     * /	POST	Crea un nuevo registro de cliente.	ClienteService.crearOActualizarCliente()
     * /{id}	GET	Obtiene la información detallada de un cliente y su historial.	ClienteService.obtenerHistorialCliente()
     * /{id}/puntos	PUT	Aplica o canjea puntos de fidelidad.	ClienteService.aplicarPuntosFidelidad()
     *
     */
}
