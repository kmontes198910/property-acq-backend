package com.kynsoft.medicaltest.application.command.examination.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.medicaltest.domain.entity.Examination;
import com.kynsoft.medicaltest.domain.service.ExaminationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Manejador para el comando de actualización de exámenes
 */
@Component
@RequiredArgsConstructor
public class UpdateExaminationCommandHandler implements ICommandHandler<UpdateExaminationCommand> {
    
    private final ExaminationService examinationService;
    
    public void handle(UpdateExaminationCommand command) {
        // Buscar el examen existente
        Examination existingExamination = examinationService.findExaminationById(command.getId())
                .orElseThrow(() -> new RuntimeException("Examen no encontrado: " + command.getId()));
        
        // Actualizar los campos
        existingExamination.setExaminationType(command.getExaminationType());
        existingExamination.setStatus(command.getStatus());
        existingExamination.setObservations(command.getObservations());
        existingExamination.setUpdatedBy(command.getUpdatedBy());
        
        // Si hay resultados, completar el examen
        if (command.getResults() != null && !command.getResults().isEmpty()) {
            existingExamination.complete(command.getResults());
        }
        
        // Persistir los cambios
        examinationService.updateExamination(existingExamination);

    }
}
