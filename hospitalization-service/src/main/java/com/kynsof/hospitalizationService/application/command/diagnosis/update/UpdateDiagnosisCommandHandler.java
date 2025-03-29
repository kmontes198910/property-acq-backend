package com.kynsof.hospitalizationService.application.command.diagnosis.update;

import com.kynsof.hospitalizationService.domain.dto.DiagnosisDto;
import com.kynsof.hospitalizationService.domain.dto.EmergencyCaseDto;
import com.kynsof.hospitalizationService.domain.service.IDiagnosisService;
import com.kynsof.hospitalizationService.domain.service.IEmergencyCaseService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class UpdateDiagnosisCommandHandler implements ICommandHandler<UpdateDiagnosisCommand> {

    private final IEmergencyCaseService emergencyCaseService;
    private final IDiagnosisService diagnosisService;

    public UpdateDiagnosisCommandHandler(IEmergencyCaseService emergencyCaseService,
                                         IDiagnosisService diagnosisService) {
        this.emergencyCaseService = emergencyCaseService;
        this.diagnosisService = diagnosisService;
    }

    @Override
    public void handle(UpdateDiagnosisCommand command) {
        EmergencyCaseDto emergencyCaseDto = this.emergencyCaseService.findById(command.getEmergencyCase());

        DiagnosisDto diagnosisDto = this.diagnosisService.findById(command.getId());
        diagnosisDto.setEmergencyCase(emergencyCaseDto);
        diagnosisDto.setDiagnosisDescription(command.getDiagnosisDescription());
        diagnosisDto.setDiagnosisType(command.getDiagnosisType());

        diagnosisService.update(diagnosisDto);
    }
}
