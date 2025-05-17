package com.kynsoft.medicaltest.application.command.examinationorder.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.medicaltest.application.command.examination.create.CreateExaminationRequest;
import com.kynsoft.medicaltest.domain.entity.Examination;
import com.kynsoft.medicaltest.domain.entity.ExaminationOrder;
import com.kynsoft.medicaltest.domain.service.ExaminationOrderService;
import com.kynsoft.medicaltest.domain.service.ExaminationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Manejador para el comando de creación de órdenes de exámenes
 */
@Component
@RequiredArgsConstructor
public class CreateExaminationOrderCommandHandler implements ICommandHandler<CreateExaminationOrderCommand, CreateExaminationOrderMessage> {
    
    private final ExaminationOrderService examinationOrderService;
    private final ExaminationService examinationService;
    
    @Override
    public CreateExaminationOrderMessage handle(CreateExaminationOrderCommand command) {
        // Crear la entidad de dominio a partir del comando
        ExaminationOrder order = ExaminationOrder.builder()
                .id(command.getId()) // Usamos el ID generado en el comando
                .patientId(command.getPatientId())
                .doctorId(command.getDoctorId())
                .creationDate(command.getCreationDate() != null ? command.getCreationDate() : LocalDateTime.now())
                .status(command.getStatus() != null ? command.getStatus() : "PENDIENTE")
                .observations(command.getObservations())
                .businessId(command.getBusinessId())
                .createdBy(command.getCreatedBy())
                .examinations(new ArrayList<>()) // Inicializamos la lista vacía
                .build();
        
        // Persistir la nueva orden
        ExaminationOrder savedOrder = examinationOrderService.createOrder(order);
        
        // Procesar y crear cada examen incluido en el comando
        if (command.getExaminations() != null && !command.getExaminations().isEmpty()) {
            for (CreateExaminationRequest examinationRequest : command.getExaminations()) {
                // Crear la entidad de examen
                Examination examination = Examination.builder()
                        .id(UUID.randomUUID())
                        .orderId(savedOrder.getId())
                        .code(examinationRequest.getCode())
                        .examinationType(examinationRequest.getExaminationType())
                        .status(examinationRequest.getStatus() != null ? examinationRequest.getStatus() : "PENDIENTE")
                        .observations(examinationRequest.getObservations())
                        .createdAt(LocalDateTime.now())
                        .createdBy(command.getCreatedBy())
                        .build();
                
                // Persistir el nuevo examen
                Examination savedExamination = examinationService.createExamination(examination);
                
                // Agregar el examen a la orden
                savedOrder.addExamination(savedExamination);
            }
            
            // Actualizar la orden con los nuevos exámenes
            savedOrder = examinationOrderService.updateOrder(savedOrder);
        }
        
        // Devolver el mensaje con el ID de la orden creada
        return new CreateExaminationOrderMessage(savedOrder.getId());
    }
}
