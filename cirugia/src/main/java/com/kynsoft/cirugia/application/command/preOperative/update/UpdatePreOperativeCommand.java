package com.kynsoft.cirugia.application.command.preOperative.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePreOperativeCommand implements ICommand {
    private UUID id;
    private UUID surgeryId;
    private String admissionReason;
    private String currentDiseaseHistory;
    private String physicalExamination;
    private String consentInformedFileUrl;
    private UUID updatedBy;
    
    public static UpdatePreOperativeCommand fromRequest(UpdatePreOperativeRequest request, UUID id, String userId) {
        return UpdatePreOperativeCommand.builder()
                .id(id)
                .surgeryId(request.getSurgeryId())
                .admissionReason(request.getAdmissionReason())
                .currentDiseaseHistory(request.getCurrentDiseaseHistory())
                .physicalExamination(request.getPhysicalExamination())
                .consentInformedFileUrl(request.getConsentInformedFileUrl())
                .updatedBy(UUID.fromString(userId))
                .build();
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdatePreOperativeMessage(id);
    }
}