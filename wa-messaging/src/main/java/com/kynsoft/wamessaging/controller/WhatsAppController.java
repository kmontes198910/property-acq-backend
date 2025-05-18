package com.kynsoft.wamessaging.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.wamessaging.application.command.create.CreateWhatsAppMessageCommand;
import com.kynsoft.wamessaging.application.command.create.CreateWhatsAppMessageMessage;
import com.kynsoft.wamessaging.application.command.create.CreateWhatsAppMessageRequest;
import com.kynsoft.wamessaging.application.dto.MessageResponse;
import com.kynsoft.wamessaging.application.dto.SendMessageRequest;
import com.kynsoft.wamessaging.application.service.MessageCoordinatorService;
import com.kynsoft.wamessaging.infrastructure.entity.MessageStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Controlador para gestionar las operaciones de mensajería WhatsApp
 */
@RestController
@RequestMapping("/api/whatsapp")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "WhatsApp Messaging", description = "API para el envío y gestión de mensajes de WhatsApp")
public class WhatsAppController {

    private final MessageCoordinatorService messageCoordinatorService;
    private final IMediator mediator;

    /**
     * Envía un mensaje de WhatsApp
     *
     * @param request Datos del mensaje a enviar
     * @return Respuesta con el ID del mensaje creado
     */
    @PostMapping("/messages")
    @Operation(
            summary = "Enviar mensaje",
            description = "Envía un mensaje de WhatsApp a un destinatario"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    examples = {
                            @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "Mensaje de recordatorio",
                                    summary = "Ejemplo de mensaje usando plantilla doctor_kyn_reminder",
                                    value = """
                                            {
                                              "recipientPhone": "593983825630",
                                              "recipientName": "Juan Perez",
                                              "messageType": "TEMPLATE",
                                              "templateName": "doctor_kyn_reminder",
                                              "messageContent": {
                                                "languageCode": "es",
                                                "headerParam": "Juan",
                                                "bodyParams": ["01-04-2025", "14:02"]
                                              }
                                            }
                                            """
                            ),
                            @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "Mensaje de pago",
                                    summary = "Ejemplo de mensaje usando plantilla doctor_kyn_paymently con botón",
                                    value = """
                                            {
                                              "recipientPhone": "593983825630",
                                              "recipientName": "Juan P",
                                              "messageType": "TEMPLATE",
                                              "templateName": "doctor_kyn_paymently",
                                              "messageContent": {
                                                "languageCode": "es",
                                                "headerParam": "Juan",
                                                "bodyParams": ["$10,22", "1234567890P"],
                                                "buttonParams":["https://tes.com"]
                                              }
                                            }
                                            """
                            ),
                            @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "Mensaje de texto simple",
                                    summary = "Ejemplo de mensaje de texto sin usar plantilla",
                                    value = """
                                            {
                                              "recipientPhone": "593983825630",
                                              "recipientName": "Keimer Montes",
                                              "messageContent": "Esto es la prueba real",
                                              "messageType": "TEXT",
                                              "templateName": ""
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Mensaje aceptado para procesamiento",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos de solicitud inválidos")
    })
    public ResponseEntity<?> sendMessage(@Valid @RequestBody CreateWhatsAppMessageRequest request) {
        log.info("Recibida solicitud para enviar mensaje a: {}", request.getRecipientPhone());

        CreateWhatsAppMessageCommand command =  CreateWhatsAppMessageCommand.fromRequest(request);
        CreateWhatsAppMessageMessage response = mediator.send(command);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    /**
     * Envía mensajes en lote a múltiples destinatarios
     *
     * @param requests Lista de solicitudes de mensajes
     * @return Lista de respuestas de mensajes creados
     */
    @PostMapping("/messages/batch")
    @Operation(summary = "Enviar lote de mensajes", description = "Envía múltiples mensajes de WhatsApp en una sola operación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Mensajes aceptados para procesamiento"),
            @ApiResponse(responseCode = "400", description = "Datos de solicitud inválidos")
    })
    public ResponseEntity<List<MessageResponse>> sendBatchMessages(@Valid @RequestBody List<SendMessageRequest> requests) {
        log.info("Recibida solicitud para enviar {} mensajes en lote", requests.size());

        List<MessageResponse> responses = requests.stream()
                .map(request -> {
                    MessageResponse response = messageCoordinatorService.queueMessage(request);
                    response.setMessage("Mensaje puesto en cola para envío");
                    return response;
                })
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responses);
    }

    /**
     * Obtiene el estado de un mensaje
     *
     * @param id ID del mensaje
     * @return Detalles del mensaje
     */
    @GetMapping("/messages/{id}")
    @Operation(summary = "Consultar estado de mensaje", description = "Obtiene el estado actual de un mensaje enviado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mensaje encontrado",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "404", description = "Mensaje no encontrado")
    })
    public ResponseEntity<MessageResponse> getMessageStatus(
            @Parameter(description = "ID del mensaje", required = true)
            @PathVariable UUID id) {
        log.info("Consultando estado del mensaje: {}", id);

        MessageResponse response = messageCoordinatorService.getMessage(id);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Busca mensajes según varios criterios
     *
     * @param startDate Fecha de inicio opcional
     * @param endDate   Fecha de fin opcional
     * @param status    Estado opcional
     * @param phone     Número de teléfono opcional
     * @param pageable  Configuración de paginación
     * @return Página de mensajes que coinciden con los criterios
     */
    @GetMapping("/messages")
    @Operation(summary = "Buscar mensajes", description = "Busca mensajes según criterios como fecha, estado y destinatario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente")
    })
    public ResponseEntity<Page<MessageResponse>> searchMessages(
            @Parameter(description = "Fecha de inicio (formato ISO)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,

            @Parameter(description = "Fecha de fin (formato ISO)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,

            @Parameter(description = "Estado del mensaje")
            @RequestParam(required = false) MessageStatus status,

            @Parameter(description = "Número de teléfono del destinatario")
            @RequestParam(required = false) String phone,

            @Parameter(description = "Configuración de paginación")
            Pageable pageable) {

        log.info("Buscando mensajes con filtros - desde: {}, hasta: {}, estado: {}, teléfono: {}",
                startDate, endDate, status, phone);

        Page<MessageResponse> response = messageCoordinatorService.searchMessages(startDate, endDate, status, phone, pageable);

        return ResponseEntity.ok(response);
    }

    /**
     * Reintentar el envío de un mensaje que ha fallado
     *
     * @param id ID del mensaje
     * @return Respuesta con el estado del reintento
     */
    @PostMapping("/messages/{id}/retry")
    @Operation(summary = "Reintentar envío", description = "Reintenta el envío de un mensaje que ha fallado anteriormente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Reintento aceptado",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Estado incorrecto para reintento"),
            @ApiResponse(responseCode = "404", description = "Mensaje no encontrado")
    })
    public ResponseEntity<MessageResponse> retryMessage(
            @Parameter(description = "ID del mensaje", required = true)
            @PathVariable UUID id) {
        log.info("Solicitando reintento de envío para el mensaje: {}", id);

        try {
            MessageResponse response = messageCoordinatorService.retryMessage(id);

            // Añadir mensaje informativo
            response.setMessage("Mensaje puesto en cola para reintento");

            return ResponseEntity.accepted().body(response);

        } catch (IllegalArgumentException e) {
            log.error("Error al reintentar mensaje: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            log.error("Estado incorrecto para reintento: {}", e.getMessage());

            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    /**
     * Cancelar un mensaje pendiente
     *
     * @param id ID del mensaje
     * @return Resultado de la cancelación
     */
    @DeleteMapping("/messages/{id}")
    @Operation(summary = "Cancelar mensaje", description = "Cancela un mensaje pendiente de envío")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mensaje cancelado correctamente",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Estado incorrecto para cancelación"),
            @ApiResponse(responseCode = "404", description = "Mensaje no encontrado")
    })
    public ResponseEntity<MessageResponse> cancelMessage(
            @Parameter(description = "ID del mensaje", required = true)
            @PathVariable UUID id) {
        log.info("Solicitando cancelación del mensaje: {}", id);

        try {
            MessageResponse response = messageCoordinatorService.cancelMessage(id);

            // Añadir mensaje informativo
            response.setMessage("Mensaje cancelado correctamente");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.error("Error al cancelar mensaje: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            log.error("Estado incorrecto para cancelación: {}", e.getMessage());

            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }
}
