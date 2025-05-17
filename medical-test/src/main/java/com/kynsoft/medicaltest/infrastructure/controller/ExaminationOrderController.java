package com.kynsoft.medicaltest.infrastructure.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.medicaltest.application.command.examinationorder.addexamination.AddExaminationToOrderCommand;
import com.kynsoft.medicaltest.application.command.examinationorder.addexamination.AddExaminationToOrderMessage;
import com.kynsoft.medicaltest.application.command.examinationorder.create.CreateExaminationOrderCommand;
import com.kynsoft.medicaltest.application.command.examinationorder.create.CreateExaminationOrderMessage;
import com.kynsoft.medicaltest.application.command.examinationorder.create.CreateExaminationOrderRequest;
import com.kynsoft.medicaltest.application.command.examinationorder.removeexamination.RemoveExaminationFromOrderCommand;
import com.kynsoft.medicaltest.application.command.examinationorder.removeexamination.RemoveExaminationFromOrderMessage;
import com.kynsoft.medicaltest.application.command.examinationorder.update.UpdateExaminationOrderCommand;
import com.kynsoft.medicaltest.application.command.examinationorder.update.UpdateExaminationOrderMessage;
import com.kynsoft.medicaltest.application.command.examinationorder.update.UpdateExaminationOrderRequest;
import com.kynsoft.medicaltest.application.query.examinationorder.ExaminationOrderListResponse;
import com.kynsoft.medicaltest.application.query.examinationorder.ExaminationOrderResponse;
import com.kynsoft.medicaltest.application.query.examinationorder.getbyid.GetExaminationOrderByIdQuery;
import com.kynsoft.medicaltest.application.query.examinationorder.list.ListExaminationOrdersQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para órdenes de exámenes médicos
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Órdenes de Exámenes", description = "API para gestionar órdenes de exámenes médicos")
public class ExaminationOrderController {
    
    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-Id";
    
    @PostMapping
    @Operation(summary = "Crear una nueva orden de exámenes", description = "Crea una nueva orden de exámenes médicos")
    public ResponseEntity<UUID> createOrder(
            @Valid @RequestBody CreateExaminationOrderRequest request,
            HttpServletRequest httpRequest) {
        
        // Obtener el ID de usuario del header
        String userId = httpRequest.getHeader(USER_ID_HEADER);
        
        // Crear el comando a partir del request
        CreateExaminationOrderCommand command = CreateExaminationOrderCommand.fromRequest(request, userId);
        
        // Enviar el comando mediante el mediador
        CreateExaminationOrderMessage message = mediator.send(command);
        
        // Devolver el ID generado
        return new ResponseEntity<>(message.getId(), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener orden por ID", description = "Obtiene una orden de exámenes por su identificador único")
    public ResponseEntity<ExaminationOrderResponse> getOrderById(@PathVariable UUID id) {
        GetExaminationOrderByIdQuery query = new GetExaminationOrderByIdQuery(id);
        ExaminationOrderResponse response = mediator.send(query);
        
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar orden", description = "Actualiza una orden de exámenes médicos existente")
    public ResponseEntity<UUID> updateOrder(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateExaminationOrderRequest request,
            HttpServletRequest httpRequest) {
        
        // Obtener el ID de usuario del header
        String userId = httpRequest.getHeader(USER_ID_HEADER);
        
        // Crear el comando a partir del request
        UpdateExaminationOrderCommand command = UpdateExaminationOrderCommand.fromRequest(id, request, userId);
        
        // Enviar el comando mediante el mediador
        UpdateExaminationOrderMessage message = mediator.send(command);
        
        // Devolver el ID actualizado
        return new ResponseEntity<>(message.getId(), HttpStatus.OK);
    }
    
    @GetMapping
    @Operation(summary = "Listar órdenes", description = "Lista órdenes de exámenes con filtros opcionales")
    public ResponseEntity<ExaminationOrderListResponse> listOrders(
            @RequestParam(required = false) UUID patientId,
            @RequestParam(required = false) UUID doctorId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) UUID businessId) {
        
        ListExaminationOrdersQuery query;
        
        if (patientId != null) {
            query = ListExaminationOrdersQuery.byPatientId(patientId);
        } else if (doctorId != null) {
            query = ListExaminationOrdersQuery.byDoctorId(doctorId);
        } else if (status != null) {
            query = ListExaminationOrdersQuery.byStatus(status);
        } else if (businessId != null) {
            query = ListExaminationOrdersQuery.byBusinessId(businessId);
        } else {
            // Si no se proporciona ningún filtro, listar órdenes creadas en las últimas 24 horas
            LocalDateTime endDate = LocalDateTime.now();
            LocalDateTime startDate = endDate.minusDays(1);
            query = ListExaminationOrdersQuery.byDateRange(startDate, endDate);
        }
        
        ExaminationOrderListResponse response = mediator.send(query);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar orden", description = "Elimina una orden de exámenes por su ID")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable UUID id,
            HttpServletRequest httpRequest) {
        
        // Esta operación podría implementarse mediante un comando de eliminación
        // Por ahora, solo verificamos si existe con una consulta
        GetExaminationOrderByIdQuery query = new GetExaminationOrderByIdQuery(id);
        ExaminationOrderResponse response = mediator.send(query);
        
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Aquí se podría crear y enviar un comando de eliminación
        // DeleteExaminationOrderCommand command = new DeleteExaminationOrderCommand(id);
        // mediator.send(command);
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PutMapping("/{orderId}/examinations/{examinationId}")
    @Operation(summary = "Agregar examen a orden", description = "Agrega un examen existente a una orden")
    public ResponseEntity<UUID> addExaminationToOrder(
            @PathVariable UUID orderId,
            @PathVariable UUID examinationId,
            HttpServletRequest httpRequest) {
        
        String userId = httpRequest.getHeader(USER_ID_HEADER);
        
        // Crear y enviar el comando para agregar el examen a la orden
        AddExaminationToOrderCommand command = AddExaminationToOrderCommand.create(orderId, examinationId, userId);
        AddExaminationToOrderMessage message = mediator.send(command);
        
        return new ResponseEntity<>(message.getOrderId(), HttpStatus.OK);
    }
    
    @DeleteMapping("/{orderId}/examinations/{examinationId}")
    @Operation(summary = "Eliminar examen de orden", description = "Elimina un examen de una orden")
    public ResponseEntity<UUID> removeExaminationFromOrder(
            @PathVariable UUID orderId,
            @PathVariable UUID examinationId,
            HttpServletRequest httpRequest) {
        
        String userId = httpRequest.getHeader(USER_ID_HEADER);
        
        // Crear y enviar el comando para eliminar el examen de la orden
        RemoveExaminationFromOrderCommand command = RemoveExaminationFromOrderCommand.create(orderId, examinationId, userId);
        RemoveExaminationFromOrderMessage message = mediator.send(command);
        
        return new ResponseEntity<>(message.getOrderId(), HttpStatus.OK);
    }
}
