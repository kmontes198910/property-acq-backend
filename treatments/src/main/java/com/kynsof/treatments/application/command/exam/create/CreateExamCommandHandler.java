package com.kynsof.treatments.application.command.exam.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.ExamDto;
import com.kynsof.treatments.domain.dto.ExternalConsultationDto;
import com.kynsof.treatments.domain.service.IExamService;
import com.kynsof.treatments.domain.service.IExternalConsultationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateExamCommandHandler implements ICommandHandler<CreateExamCommand> {

    private final IExamService serviceImpl;
    private final IExternalConsultationService externalConsultationService;

    public CreateExamCommandHandler(IExamService serviceImpl, IExternalConsultationService externalConsultationService) {
        this.serviceImpl = serviceImpl;
        this.externalConsultationService = externalConsultationService;
    }

    @Override
    public void handle(CreateExamCommand command) {
        ExternalConsultationDto externalConsultationDto = this.externalConsultationService.findById(command.getExternalConsultationId());
        ExamDto create = new ExamDto(
                command.getId(), 
                command.getName(), 
                command.getDescription(), 
                command.getType(),
                command.getCode(),
               externalConsultationDto
        );
        create.setCode(command.getCode());

        UUID id = serviceImpl.create(create);
        command.setId(id);
    }
}
