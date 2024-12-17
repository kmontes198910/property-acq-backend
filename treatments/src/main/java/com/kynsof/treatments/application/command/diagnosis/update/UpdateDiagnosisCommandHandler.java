package com.kynsof.treatments.application.command.diagnosis.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.DiagnosisDto;
import com.kynsof.treatments.domain.dto.ExternalConsultationDto;
import com.kynsof.treatments.domain.service.IDiagnosisService;
import com.kynsof.treatments.domain.service.IExternalConsultationService;
import org.springframework.stereotype.Component;

@Component
public class UpdateDiagnosisCommandHandler implements ICommandHandler<UpdateDiagnosisCommand> {

    private final IDiagnosisService serviceImpl;
    private final IExternalConsultationService externalConsultationService;

    public UpdateDiagnosisCommandHandler(IDiagnosisService serviceImpl, IExternalConsultationService externalConsultationService) {
        this.serviceImpl = serviceImpl;
        this.externalConsultationService = externalConsultationService;
    }

    @Override
    public void handle(UpdateDiagnosisCommand command) {
        ExternalConsultationDto externalConsultationDto = this.externalConsultationService.findById(command.getIdExternalConsultation());
        DiagnosisDto dto = serviceImpl.findById(command.getId());
        dto.setExternalConsultation(externalConsultationDto);
        dto.setDescription(command.getDescription());
        dto.setIcdCode(command.getIcdCode());
        serviceImpl.update(dto);
    }
}
