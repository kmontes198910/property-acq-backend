package com.kynsoft.medicaltest.application.command.examination.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.medicaltest.domain.entity.Examination;
import com.kynsoft.medicaltest.domain.service.ExaminationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Manejador para el comando de creación de exámenes
 */
@Component
@RequiredArgsConstructor
public class CreateExaminationCommandHandler implements ICommandHandler<CreateExaminationCommand> {
    
    private final ExaminationService examinationService;
    
    public void handle(CreateExaminationCommand command) {
        // Crear la entidad de dominio a partir del comando
        Examination examination = Examination.builder()
                .id(command.getId()) // Usamos el ID generado en el comando
                .orderId(command.getOrderId())
                .code(command.getCode())
                .examinationType(command.getExaminationType())
                .status(command.getStatus() != null ? command.getStatus() : "PENDIENTE")
                .observations(command.getObservations())
                .createdBy(command.getCreatedBy())
                .build();
        
        // Persistir el nuevo examen
        examinationService.createExamination(examination);
        

    }
}
