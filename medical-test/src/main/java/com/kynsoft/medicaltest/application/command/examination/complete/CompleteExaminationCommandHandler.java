package com.kynsoft.medicaltest.application.command.examination.complete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.medicaltest.domain.entity.Examination;
import com.kynsoft.medicaltest.domain.service.ExaminationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompleteExaminationCommandHandler implements ICommandHandler<CompleteExaminationCommand> {
    private final ExaminationService examinationService;
    
    @Override
    public void handle(CompleteExaminationCommand command) {
        Examination completedExamination = examinationService.completeExamination(command.getId(), command.getResults());
        if (completedExamination == null) {
            throw new RuntimeException("Error al completar el examen: " + command.getId());
        }
    }
}
