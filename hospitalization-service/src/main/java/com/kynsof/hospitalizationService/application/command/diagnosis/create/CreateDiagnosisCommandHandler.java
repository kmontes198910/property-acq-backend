package com.kynsof.hospitalizationService.application.command.diagnosis.create;

import com.kynsof.hospitalizationService.domain.dto.DiagnosisDto;
import com.kynsof.hospitalizationService.domain.dto.EmergencyCaseDto;
import com.kynsof.hospitalizationService.domain.service.IDiagnosisService;
import com.kynsof.hospitalizationService.domain.service.IEmergencyCaseService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class CreateDiagnosisCommandHandler implements ICommandHandler<CreateDiagnosisCommand> {

    private final IEmergencyCaseService emergencyCaseService;
    private final IDiagnosisService diagnosisService;

    public CreateDiagnosisCommandHandler(IEmergencyCaseService emergencyCaseService,
                                         IDiagnosisService diagnosisService) {
        this.emergencyCaseService = emergencyCaseService;
        this.diagnosisService = diagnosisService;
    }

    @Override
    public void handle(CreateDiagnosisCommand command) {
        EmergencyCaseDto emergencyCaseDto = this.emergencyCaseService.findById(command.getEmergencyCase());
        diagnosisService.create(new DiagnosisDto(command.getId(), emergencyCaseDto, command.getDiagnosisType(), command.getDiagnosisDescription(), command.getCode()));
    }
}
