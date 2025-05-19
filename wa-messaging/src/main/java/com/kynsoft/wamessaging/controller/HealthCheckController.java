package com.kynsoft.wamessaging.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para verificar el estado de la API
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Health", description = "API para verificar el estado del servicio")
public class HealthCheckController {

    /**
     * Comprueba el estado de la API
     * 
     * @return Estado de la API
     */
    @GetMapping(value = "/api/health", produces = "application/json")
    @Operation(summary = "Verificar estado", description = "Comprueba si la API está funcionando correctamente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API funcionando correctamente")
    })
    public ResponseEntity<Map<String, Object>> checkHealth() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "WhatsApp Messaging API is running");
        response.put("timestamp", System.currentTimeMillis());
        
        log.info("Health check realizado: {}", response);
        return ResponseEntity.ok(response);
    }
}
