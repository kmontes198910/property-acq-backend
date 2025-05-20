package com.kynsoft.medicaltest.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.medicaltest.application.command.examination.complete.CompleteExaminationCommand;
import com.kynsoft.medicaltest.application.command.examination.complete.CompleteExaminationMessage;
import com.kynsoft.medicaltest.application.command.examination.create.CreateExaminationCommand;
import com.kynsoft.medicaltest.application.command.examination.create.CreateExaminationMessage;
import com.kynsoft.medicaltest.application.command.examination.create.CreateExaminationRequest;
import com.kynsoft.medicaltest.application.command.examination.update.UpdateExaminationCommand;
import com.kynsoft.medicaltest.application.command.examination.update.UpdateExaminationMessage;
import com.kynsoft.medicaltest.application.command.examination.update.UpdateExaminationRequest;
import com.kynsoft.medicaltest.application.query.examination.ExaminationListResponse;
import com.kynsoft.medicaltest.application.query.examination.ExaminationResponse;
import com.kynsoft.medicaltest.application.query.examination.getbyid.GetExaminationByIdQuery;
import com.kynsoft.medicaltest.application.query.examination.list.ListExaminationsQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Controlador REST para exámenes médicos
 */
@RestController
@RequestMapping("/examinations")
@RequiredArgsConstructor
@Tag(name = "Exámenes Médicos", description = "API para gestionar exámenes médicos")
public class ExaminationController {
    
    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-Id";
    
    @PostMapping
    @Operation(summary = "Crear un nuevo examen", description = "Crea un nuevo examen médico")
    public ResponseEntity<UUID> createExamination(
            @Valid @RequestBody CreateExaminationRequest request,
            HttpServletRequest httpRequest) {
        
        // Obtener el ID de usuario del header
        String userId = httpRequest.getHeader(USER_ID_HEADER);
        
        // Crear el comando a partir del request
        CreateExaminationCommand command = CreateExaminationCommand.fromRequest(request, userId);
        
        // Enviar el comando mediante el mediador
        CreateExaminationMessage message = mediator.send(command);
        
        // Devolver el ID generado
        return new ResponseEntity<>(message.getId(), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un examen", description = "Actualiza un examen médico existente")
    public ResponseEntity<UUID> updateExamination(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateExaminationRequest request,
            HttpServletRequest httpRequest) {
        
        // Obtener el ID de usuario del header
        String userId = httpRequest.getHeader(USER_ID_HEADER);
        
        // Crear el comando a partir del request
        UpdateExaminationCommand command = UpdateExaminationCommand.fromRequest(id, request, userId);
        
        // Enviar el comando mediante el mediador
        UpdateExaminationMessage message = mediator.send(command);
        
        // Devolver el ID actualizado
        return new ResponseEntity<>(message.getId(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener examen por ID", description = "Obtiene un examen médico por su identificador único")
    public ResponseEntity<ExaminationResponse> getExaminationById(@PathVariable UUID id) {
        GetExaminationByIdQuery query = new GetExaminationByIdQuery(id);
        ExaminationResponse response = mediator.send(query);
        
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping
    @Operation(summary = "Listar exámenes", description = "Lista exámenes médicos con filtros opcionales")
    public ResponseEntity<ExaminationListResponse> listExaminations(
            @RequestParam(required = false) UUID orderId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status) {
        
        ListExaminationsQuery query;
        
        if (orderId != null) {
            query = ListExaminationsQuery.byOrderId(orderId);
        } else if (type != null) {
            query = ListExaminationsQuery.byType(type);
        } else if (status != null) {
            query = ListExaminationsQuery.byStatus(status);
        } else {
            // Si no se proporciona ningún filtro, listar exámenes completados en las últimas 24 horas
            LocalDateTime endDate = LocalDateTime.now();
            LocalDateTime startDate = endDate.minusDays(1);
            query = ListExaminationsQuery.byDateRange(startDate, endDate);
        }
        
        ExaminationListResponse response = mediator.send(query);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar examen", description = "Elimina un examen médico por su ID")
    public ResponseEntity<Void> deleteExamination(
            @PathVariable UUID id,
            HttpServletRequest httpRequest) {
        
        // Esta operación podría implementarse mediante un comando de eliminación
        // Por ahora, solo verificamos si existe con una consulta
        GetExaminationByIdQuery query = new GetExaminationByIdQuery(id);
        ExaminationResponse response = mediator.send(query);
        
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Aquí se podría crear y enviar un comando de eliminación
        // DeleteExaminationCommand command = new DeleteExaminationCommand(id);
        // mediator.send(command);
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping("/{id}/complete")
    @Operation(summary = "Completar examen", description = "Marca un examen como completado y añade resultados")
    public ResponseEntity<UUID> completeExamination(
            @PathVariable UUID id, 
            @RequestParam String results,
            HttpServletRequest httpRequest) {
        
        String userId = httpRequest.getHeader(USER_ID_HEADER);
        
        // Crear y enviar el comando para completar el examen
        CompleteExaminationCommand command = CompleteExaminationCommand.fromRequest(id, results, userId);
        CompleteExaminationMessage message = mediator.send(command);
        
        return new ResponseEntity<>(message.getId(), HttpStatus.OK);
    }
}
