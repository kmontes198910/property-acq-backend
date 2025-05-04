package com.kynsoft.cirugia.application.command.preOperative.create;

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
public class CreatePreOperativeCommand implements ICommand {
    private UUID id;
    private UUID surgeryId;
    private String admissionReason;
    private String currentDiseaseHistory;
    private String physicalExamination;
    private UUID createdBy;
    
    public static CreatePreOperativeCommand fromRequest(CreatePreOperativeRequest request, String userId) {
        return CreatePreOperativeCommand.builder()
                .id(UUID.randomUUID())
                .surgeryId(request.getSurgeryId())
                .admissionReason(request.getAdmissionReason())
                .currentDiseaseHistory(request.getCurrentDiseaseHistory())
                .physicalExamination(request.getPhysicalExamination())
                .createdBy(UUID.fromString(userId))
                .build();
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreatePreOperativeMessage(id);
    }
}