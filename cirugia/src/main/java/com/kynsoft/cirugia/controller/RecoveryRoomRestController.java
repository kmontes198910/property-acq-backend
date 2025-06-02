package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.command.recoveryroom.create.CreateRecoveryRoomCommand;
import com.kynsoft.cirugia.application.command.recoveryroom.create.CreateRecoveryRoomMessage;
import com.kynsoft.cirugia.application.command.recoveryroom.create.CreateRecoveryRoomRequest;
import com.kynsoft.cirugia.application.command.recoveryroom.delete.DeleteRecoveryRoomCommand;
import com.kynsoft.cirugia.application.command.recoveryroom.delete.DeleteRecoveryRoomMessage;
import com.kynsoft.cirugia.application.command.recoveryroom.update.UpdateRecoveryRoomCommand;
import com.kynsoft.cirugia.application.command.recoveryroom.update.UpdateRecoveryRoomMessage;
import com.kynsoft.cirugia.application.command.recoveryroom.update.UpdateRecoveryRoomRequest;
import com.kynsoft.cirugia.application.query.recoveryroom.getbyid.GetRecoveryRoomByIdQuery;
import com.kynsoft.cirugia.application.query.recoveryroom.getbyid.RecoveryRoomResponse;
import com.kynsoft.cirugia.application.query.recoveryroom.search.SearchRecoveryRoomsQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recovery-rooms")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Recovery Rooms", description = "API para la gestión de salas de recuperación")
public class RecoveryRoomRestController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_NAME_HEADER = "X-User-Name";

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una sala de recuperación por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sala de recuperación encontrada"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Sala de recuperación no encontrada")
    })
    public ResponseEntity<?> getById(
            @Parameter(description = "ID de la sala de recuperación", required = true)
            @PathVariable UUID id) {
        
        log.info("REST Request - Buscando sala de recuperación con ID: {}", id);
        
        GetRecoveryRoomByIdQuery query = GetRecoveryRoomByIdQuery.builder()
                .id(id)
                .build();
        
        RecoveryRoomResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }



    @PostMapping
    @Operation(summary = "Crear una nueva sala de recuperación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Sala de recuperación creada"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<?> create(
            @Parameter(description = "Datos de la sala de recuperación", required = true)
            @RequestBody CreateRecoveryRoomRequest request,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId) {
        
        log.info("REST Request - Creando nueva sala de recuperación: {}", request.getName());
        
        CreateRecoveryRoomCommand command = CreateRecoveryRoomCommand.fromRequest(
                request,
                UUID.fromString(userId)
        );
        
        CreateRecoveryRoomMessage response = mediator.send(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar una sala de recuperación existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sala de recuperación actualizada"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Sala de recuperación no encontrada")
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID de la sala de recuperación", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Datos actualizados de la sala", required = true)
            @RequestBody UpdateRecoveryRoomRequest request,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId) {
        
        log.info("REST Request - Actualizando sala de recuperación con ID: {}", id);

        
        UpdateRecoveryRoomCommand command = UpdateRecoveryRoomCommand.fromRequest(
                request, 
                id,
                UUID.fromString(userId)
        );
        
        UpdateRecoveryRoomMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una sala de recuperación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Sala de recuperación eliminada"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Sala de recuperación no encontrada")
    })
    public ResponseEntity<?> delete(
            @Parameter(description = "ID de la sala de recuperación a eliminar", required = true)
            @PathVariable UUID id) {
        
        log.info("REST Request - Eliminando sala de recuperación con ID: {}", id);
        
        DeleteRecoveryRoomCommand command = DeleteRecoveryRoomCommand.builder()
                .id(id)
                .build();
        
        DeleteRecoveryRoomMessage response = mediator.send(command);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    @Operation(summary = "Buscar salas de recuperación con criterios de filtrado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud de búsqueda inválida")
    })
    public ResponseEntity<?> search(
            @Parameter(description = "Parámetros de búsqueda", required = true)
            @RequestBody SearchRequest request) {
        
        log.info("REST Request - Buscando salas de recuperación con filtros: {}", 
                request.getFilter() != null ? request.getFilter().size() : 0);
        
        SearchRecoveryRoomsQuery query = new SearchRecoveryRoomsQuery(request);
        PaginatedResponse response = mediator.send(query);
        
        return ResponseEntity.ok(response);
    }
}