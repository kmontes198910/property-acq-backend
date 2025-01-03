package com.kynsof.treatments.application.command.diagnosis.create;

import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.DiagnosisDto;
import com.kynsof.treatments.domain.dto.ExternalConsultationDto;
import com.kynsof.treatments.domain.rules.externalconsultation.ExternalConsultationCreateAtNotEqualsRule;
import com.kynsof.treatments.domain.service.IDiagnosisService;
import com.kynsof.treatments.domain.service.IExternalConsultationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateDiagnosisCommandHandler implements ICommandHandler<CreateDiagnosisCommand> {

    private final IDiagnosisService serviceImpl;
    private final IExternalConsultationService externalConsultationService;

    public CreateDiagnosisCommandHandler(IDiagnosisService serviceImpl, IExternalConsultationService externalConsultationService) {
        this.serviceImpl = serviceImpl;
        this.externalConsultationService = externalConsultationService;
    }

    @Override
    public void handle(CreateDiagnosisCommand command) {
        ExternalConsultationDto externalConsultationDto = this.externalConsultationService.findById(command.getIdExternalConsultation());
        RulesChecker.checkRule(new ExternalConsultationCreateAtNotEqualsRule(externalConsultationDto.getConsultationTime()));
        UUID id = UUID.randomUUID();
        DiagnosisDto dto = new DiagnosisDto(id, command.getIcdCode(), command.getDescription(), externalConsultationDto);
        serviceImpl.create(dto);
        command.setId(id);

    }
}
