package com.kynsof.treatments.application.command.exam.create;

import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.ExamDto;
import com.kynsof.treatments.domain.dto.ExternalConsultationDto;
import com.kynsof.treatments.domain.evnts.CreateBillingEvent;
import com.kynsof.treatments.domain.rules.externalconsultation.ExternalConsultationCreateAtNotEqualsRule;
import com.kynsof.treatments.domain.service.IExamService;
import com.kynsof.treatments.domain.service.IExternalConsultationService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CreateExamCommandHandler implements ICommandHandler<CreateExamCommand> {

    private final IExamService serviceImpl;
    private final IExternalConsultationService externalConsultationService;
    private final ApplicationEventPublisher applicationEventPublisher;
    public CreateExamCommandHandler(IExamService serviceImpl, IExternalConsultationService externalConsultationService, ApplicationEventPublisher applicationEventPublisher) {
        this.serviceImpl = serviceImpl;
        this.externalConsultationService = externalConsultationService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void handle(CreateExamCommand command) {
        ExternalConsultationDto externalConsultationDto = this.externalConsultationService.findById(command.getExternalConsultationId());
        RulesChecker.checkRule(new ExternalConsultationCreateAtNotEqualsRule(externalConsultationDto.getConsultationTime()));
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
        List<String> codes = new ArrayList<>();
        codes.add(command.getCode());
        CreateBillingEvent createBillingEvent = new CreateBillingEvent(
                externalConsultationDto.getPatient().getId(),
                externalConsultationDto.getBusiness().getId(),
                codes
        );

        applicationEventPublisher.publishEvent(createBillingEvent);
    }
}
