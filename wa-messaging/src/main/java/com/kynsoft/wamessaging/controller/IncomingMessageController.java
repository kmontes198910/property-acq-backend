package com.kynsoft.wamessaging.controller;

import com.kynsoft.wamessaging.application.query.getMessage.IncomingMessageResponse;
import com.kynsoft.wamessaging.domain.service.IncomingMessageService;
import com.kynsoft.wamessaging.infrastructure.entity.IncomingMessage;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador para gestionar los mensajes entrantes de WhatsApp
 */
@RestController
@RequestMapping("/api/whatsapp/messages/incoming")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Mensajes Entrantes de WhatsApp", description = "Endpoints para consultar mensajes recibidos de WhatsApp")
public class IncomingMessageController {

    private final IncomingMessageService incomingMessageService;

    /**
     * Obtiene todos los mensajes entrantes con paginación
     */
    @GetMapping
    @Operation(summary = "Listar mensajes", description = "Obtiene todos los mensajes entrantes de WhatsApp con paginación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de mensajes obtenido correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = IncomingMessageResponse.class)))
    })
    public ResponseEntity<Page<IncomingMessageResponse>> getAllMessages(
            @Parameter(description = "Número de página", required = false)
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página", required = false)
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "receivedAt"));
        Page<IncomingMessage> messages = incomingMessageService.getAllMessages(pageable);
        
        Page<IncomingMessageResponse> response = messages.map(this::mapToResponse);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene mensajes de un remitente específico
     */
    @GetMapping("/from/{senderPhone}")
    @Operation(summary = "Buscar por remitente", description = "Obtiene todos los mensajes de un remitente específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mensajes encontrados correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = IncomingMessageResponse.class)))
    })
    public ResponseEntity<List<IncomingMessageResponse>> getMessagesBySender(
            @Parameter(description = "Número de teléfono del remitente", required = true)
            @PathVariable String senderPhone) {
        
        List<IncomingMessage> messages = incomingMessageService.getMessagesBySender(senderPhone);
        List<IncomingMessageResponse> response = messages.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene mensajes por fecha
     */
    @GetMapping("/by-date")
    @Operation(summary = "Buscar por fecha", description = "Obtiene todos los mensajes recibidos en una fecha específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mensajes encontrados correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = IncomingMessageResponse.class)))
    })
    public ResponseEntity<Page<IncomingMessageResponse>> getMessagesByDate(
            @Parameter(description = "Fecha de inicio (formato yyyy-MM-dd)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Parameter(description = "Número de página", required = false)
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página", required = false)
            @RequestParam(defaultValue = "20") int size) {
        
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "receivedAt"));
        Page<IncomingMessage> messages = incomingMessageService.getMessagesBetweenDates(startOfDay, endOfDay, pageable);
        
        Page<IncomingMessageResponse> response = messages.map(this::mapToResponse);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un mensaje por su ID
     */
    @GetMapping("/{messageId}")
    @Operation(summary = "Buscar por ID", description = "Obtiene un mensaje específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mensaje encontrado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = IncomingMessageResponse.class))),
        @ApiResponse(responseCode = "404", description = "Mensaje no encontrado")
    })
    public ResponseEntity<IncomingMessageResponse> getMessageById(
            @Parameter(description = "ID del mensaje", required = true)
            @PathVariable String messageId) {
        
        Optional<IncomingMessage> messageOpt = incomingMessageService.getMessageById(messageId);
        
        return messageOpt
                .map(message -> ResponseEntity.ok(mapToResponse(message)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Mapea una entidad IncomingMessage a su DTO de respuesta
     */
    private IncomingMessageResponse mapToResponse(IncomingMessage message) {
        return IncomingMessageResponse.builder()
                .messageId(message.getMessageId())
                .senderPhone(message.getSenderPhone())
                .senderName(message.getSenderName())
                .receivedAt(message.getReceivedAt())
                .serverTimestamp(message.getServerTimestamp())
                .messageType(message.getMessageType())
                .status(message.getStatus())
                .content(message.getContent())
                .mediaUrl(message.getMediaUrl())
                .mimeType(message.getMimeType())
                .caption(message.getCaption())
                .contextMessageId(message.getContextMessageId())
                .replyTo(message.getReplyTo())
                .latitude(message.getLatitude())
                .longitude(message.getLongitude())
                .locationName(message.getLocationName())
                .locationAddress(message.getLocationAddress())
                .contacts(message.getContacts())
                .build();
    }
}
