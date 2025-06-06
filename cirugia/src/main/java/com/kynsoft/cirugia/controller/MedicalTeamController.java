package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.command.medicalteam.create.CreateMedicalTeamCommand;
import com.kynsoft.cirugia.application.command.medicalteam.create.CreateMedicalTeamMessage;
import com.kynsoft.cirugia.application.command.medicalteam.create.CreateMedicalTeamRequest;
import com.kynsoft.cirugia.application.command.medicalteam.delete.DeleteMedicalTeamCommand;
import com.kynsoft.cirugia.application.query.medicalteam.getbysurgeryid.GetMedicalTeamBySurgeryIdQuery;
import com.kynsoft.cirugia.application.query.medicalteam.getbysurgeryid.MedicalTeamListResponse;
import com.kynsoft.cirugia.application.query.medicalteam.search.SearchMedicalTeamsQuery;.kynsoft.cirugia.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.command.medicalteam.create.CreateMedicalTeamCommand;
import com.kynsoft.cirugia.application.command.medicalteam.create.CreateMedicalTeamMessage;
import com.kynsoft.cirugia.application.command.medicalteam.create.CreateMedicalTeamRequest;
import com.kynsoft.cirugia.application.command.medicalteam.delete.DeleteMedicalTeamCommand;
import com.kynsoft.cirugia.application.query.medicalteam.getbysurgeryid.GetMedicalTeamBySurgeryIdQuery;
import com.kynsoft.cirugia.application.query.medicalteam.getbysurgeryid.MedicalTeamListResponse;
import com.kynsoft.cirugia.application.query.medicalteam.search.SearchMedicalTeamsQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/medical-teams")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Medical Teams", description = "API para la gestión de equipos médicos")
public class MedicalTeamController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_NAME_HEADER = "X-User-Name";

    /**
     * Crea un nuevo miembro del equipo médico
     * @param request Datos del miembro del equipo médico a crear
     * @return Respuesta con status 201 (Created) y el ID del miembro creado
     */
    @Operation(summary = "Crear un nuevo miembro del equipo médico", 
               description = "Crea un nuevo miembro del equipo médico con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Miembro del equipo médico creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> createMedicalTeam(
            @Parameter(description = "Datos del miembro del equipo médico", required = true)
            @RequestBody CreateMedicalTeamRequest request,
            @RequestHeader(value = USER_ID_HEADER, required = true) String userId ,
            @RequestHeader(value = USER_NAME_HEADER, required = true) String userName) {

        logUserInfo(userId, userName);
        if (userId != null) {
            try {
                // Verificar que el formato del UUID sea válido
                UUID.fromString(userId);
            } catch (IllegalArgumentException e) {
                log.warn("Invalid user ID format in header: {}", userId);
            }
        }

        assert userId != null;
        CreateMedicalTeamCommand command = CreateMedicalTeamCommand.fromRequest(request,UUID.fromString(userId));
        CreateMedicalTeamMessage response =mediator.send(command);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Elimina un miembro del equipo médico
     * @param id ID del miembro del equipo médico a eliminar
     * @return Respuesta con status 204 (No Content)
     */
    @Operation(summary = "Eliminar un miembro del equipo médico", 
               description = "Elimina permanentemente un miembro del equipo médico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Miembro del equipo médico eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Miembro del equipo médico no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalTeam(
            @Parameter(description = "ID del miembro del equipo médico a eliminar", required = true)
            @PathVariable UUID id,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId,
            @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        
        logUserInfo(userId, userName);
        DeleteMedicalTeamCommand command = new DeleteMedicalTeamCommand(id);
        mediator.send(command);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Busca miembros del equipo médico con filtros personalizados y paginación
     * @param request Parámetros de búsqueda
     * @return Respuesta paginada con los miembros del equipo médico que coinciden con los criterios de búsqueda
     */
    @Operation(summary = "Buscar miembros del equipo médico", 
               description = "Busca miembros del equipo médico con filtros personalizados y paginación")
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
        SearchMedicalTeamsQuery query = new SearchMedicalTeamsQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }
    
    /**
     * Obtiene todos los miembros del equipo médico para una cirugía específica
     * @param surgeryId ID de la cirugía
     * @return Lista de miembros del equipo médico para la cirugía especificada
     */
    @Operation(summary = "Obtener equipo médico por ID de cirugía", 
               description = "Obtiene todos los integrantes del equipo médico asociados a una cirugía específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
        @ApiResponse(responseCode = "404", description = "No se encontraron miembros del equipo médico para la cirugía especificada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/surgery/{surgeryId}")
    public ResponseEntity<?> findBySurgeryId(
            @Parameter(description = "ID de la cirugía", required = true)
            @PathVariable UUID surgeryId,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId,
            @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        
        logUserInfo(userId, userName);
        log.info("Fetching medical team members for surgery ID: {}", surgeryId);
        
        com.kynsoft.cirugia.application.query.medicalteam.getbysurgeryid.GetMedicalTeamBySurgeryIdQuery query = 
                new com.kynsoft.cirugia.application.query.medicalteam.getbysurgeryid.GetMedicalTeamBySurgeryIdQuery(surgeryId);
        com.kynsoft.cirugia.application.query.medicalteam.getbysurgeryid.MedicalTeamListResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Verifica si un miembro específico forma parte del equipo médico de una cirugía
     * @param surgeryId ID de la cirugía
     * @param memberId ID del miembro del equipo médico
     * @return true si el miembro forma parte del equipo médico de la cirugía, false en caso contrario
     */
    @Operation(summary = "Verificar si un miembro forma parte del equipo médico de una cirugía", 
               description = "Verifica si un miembro específico está asignado al equipo médico de una cirugía determinada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/surgery/{surgeryId}/member/{memberId}")
    public ResponseEntity<?> isMemberInSurgeryTeam(
            @Parameter(description = "ID de la cirugía", required = true)
            @PathVariable UUID surgeryId,
            @Parameter(description = "ID del miembro", required = true)
            @PathVariable UUID memberId,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId,
            @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        
        logUserInfo(userId, userName);
        log.info("Checking if member ID: {} is part of the medical team for surgery ID: {}", memberId, surgeryId);
        
        com.kynsoft.cirugia.application.query.medicalteam.ismemberinsurgery.IsMemberInSurgeryTeamQuery query = 
                new com.kynsoft.cirugia.application.query.medicalteam.ismemberinsurgery.IsMemberInSurgeryTeamQuery(surgeryId, memberId);
        com.kynsoft.cirugia.application.query.medicalteam.ismemberinsurgery.IsMemberInSurgeryTeamResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
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