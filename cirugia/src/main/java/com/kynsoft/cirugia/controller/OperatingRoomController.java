package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.query.operatingroom.getbyid.OperatingRoomResponse;
import com.kynsoft.cirugia.application.query.operatingroom.OperatingRoomSearchResponse;
import com.kynsoft.cirugia.application.query.operatingroom.getbyid.GetOperatingRoomByIdQuery;
import com.kynsoft.cirugia.application.query.operatingroom.listbybusiness.ListOperatingRoomsByBusinessQuery;
import com.kynsoft.cirugia.application.query.operatingroom.search.SearchOperatingRoomsQuery;
import com.kynsoft.cirugia.application.command.operatingroom.create.CreateOperatingRoomCommand;
import com.kynsoft.cirugia.application.command.operatingroom.create.CreateOperatingRoomRequest;
import com.kynsoft.cirugia.application.command.operatingroom.update.UpdateOperatingRoomCommand;
import com.kynsoft.cirugia.application.command.operatingroom.update.UpdateOperatingRoomRequest;
import com.kynsoft.cirugia.application.command.operatingroom.changestatus.ChangeOperatingRoomStatusCommand;
import com.kynsoft.cirugia.application.command.operatingroom.changestatus.ChangeOperatingRoomStatusRequest;
import com.kynsoft.cirugia.application.command.operatingroom.delete.DeleteOperatingRoomCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/operating-rooms")
@Slf4j
@Tag(name = "Operating Rooms", description = "API para la gestión de salas quirúrgicas")
public class OperatingRoomController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_NAME_HEADER = "X-User-Name";

    public OperatingRoomController(IMediator mediator) {
        this.mediator = mediator;
    }

    @Operation(summary = "Obtener una sala quirúrgica por ID", 
               description = "Retorna la información detallada de una sala quirúrgica específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sala quirúrgica encontrada",
                     content = @Content(schema = @Schema(implementation = OperatingRoomResponse.class))),
        @ApiResponse(responseCode = "404", description = "Sala quirúrgica no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OperatingRoomResponse> getById(
            @Parameter(description = "ID de la sala quirúrgica", required = true)
            @PathVariable UUID id,
            @Parameter(description = "ID del usuario que realiza la petición")
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId,
            @Parameter(description = "Nombre del usuario que realiza la petición")
            @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        
        logUserInfo(userId, userName);
        GetOperatingRoomByIdQuery query = new GetOperatingRoomByIdQuery(id);
        OperatingRoomResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar salas quirúrgicas por negocio", 
               description = "Retorna todas las salas quirúrgicas disponibles para un negocio específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de salas quirúrgicas obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/business/{businessId}")
    public ResponseEntity<OperatingRoomSearchResponse> listByBusiness(
            @Parameter(description = "ID del negocio", required = true)
            @PathVariable UUID businessId,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId,
            @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        
        logUserInfo(userId, userName);
        ListOperatingRoomsByBusinessQuery query = new ListOperatingRoomsByBusinessQuery(businessId);
        OperatingRoomSearchResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar salas quirúrgicas", 
               description = "Busca salas quirúrgicas con filtros personalizados y paginación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Parámetros de búsqueda inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(
            @Parameter(description = "Parámetros de búsqueda", required = true)
            @RequestBody SearchRequest request,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId,
            @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        
        logUserInfo(userId, userName);
        Pageable pageable = PageableUtil.createPageable(request);
        SearchOperatingRoomsQuery query = new SearchOperatingRoomsQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Crear una nueva sala quirúrgica", 
               description = "Crea un nuevo registro de sala quirúrgica con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Sala quirúrgica creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> createOperatingRoom(
            @Parameter(description = "Datos de la sala quirúrgica", required = true)
            @RequestBody CreateOperatingRoomRequest request,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId,
            @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        
        logUserInfo(userId, userName);
        if (userId != null) {
            try {
                UUID userUuid = UUID.fromString(userId);
            } catch (IllegalArgumentException e) {
                log.warn("Invalid user ID format in header: {}", userId);
            }
        }
        
        CreateOperatingRoomCommand command = CreateOperatingRoomCommand.fromRequest(request, userId);
        mediator.send(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Actualizar una sala quirúrgica", 
               description = "Actualiza un registro existente de sala quirúrgica con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sala quirúrgica actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Sala quirúrgica no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateOperatingRoom(
            @Parameter(description = "ID de la sala quirúrgica a actualizar", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Datos actualizados de la sala quirúrgica", required = true)
            @RequestBody UpdateOperatingRoomRequest request,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId,
            @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        
        logUserInfo(userId, userName);
        request.setOperatingRoomId(id);
        
        if (userId != null) {
            try {
                UUID userUuid = UUID.fromString(userId);
                request.setUpdatedBy(userUuid);
            } catch (IllegalArgumentException e) {
                log.warn("Invalid user ID format in header: {}", userId);
            }
        }
        
        UpdateOperatingRoomCommand command = UpdateOperatingRoomCommand.fromRequest(request);
        mediator.send(command);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Cambiar el estado de una sala quirúrgica", 
               description = "Actualiza el estado de una sala quirúrgica (disponible, en mantenimiento, etc.)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado de la sala quirúrgica actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Estado inválido"),
        @ApiResponse(responseCode = "404", description = "Sala quirúrgica no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> changeOperatingRoomStatus(
            @Parameter(description = "ID de la sala quirúrgica", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Datos del nuevo estado", required = true)
            @RequestBody ChangeOperatingRoomStatusRequest request,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId,
            @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        
        logUserInfo(userId, userName);
        request.setOperatingRoomId(id);
        
        if (userId != null) {
            try {
                UUID userUuid = UUID.fromString(userId);
                request.setUpdatedBy(userUuid);
            } catch (IllegalArgumentException e) {
                log.warn("Invalid user ID format in header: {}", userId);
            }
        }
        
        ChangeOperatingRoomStatusCommand command = ChangeOperatingRoomStatusCommand.fromRequest(request);
        mediator.send(command);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Eliminar una sala quirúrgica", 
               description = "Elimina permanentemente un registro de sala quirúrgica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Sala quirúrgica eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Sala quirúrgica no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOperatingRoom(
            @Parameter(description = "ID de la sala quirúrgica a eliminar", required = true)
            @PathVariable UUID id,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId,
            @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        
        logUserInfo(userId, userName);
        DeleteOperatingRoomCommand command = new DeleteOperatingRoomCommand(id);
        mediator.send(command);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Método auxiliar para registrar información del usuario en los logs
     */
    private void logUserInfo(String userId, String userName) {
        if (userId != null && userName != null) {
            log.info("Request from user: {} (ID: {})", userName, userId);
        } else if (userId != null) {
            log.info("Request from user ID: {}", userId);
        } else if (userName != null) {
            log.info("Request from user: {}", userName);
        } else {
            log.info("Request without user information");
        }
    }
}