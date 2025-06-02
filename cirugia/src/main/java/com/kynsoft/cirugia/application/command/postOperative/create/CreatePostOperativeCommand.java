package com.kynsoft.cirugia.application.command.postOperative.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostOperativeCommand implements ICommand {
    private UUID id;
    private UUID surgeryId;
    private String treatmentSummary;
    private String dischargeInstructions;
    private String lifeStatus;
    private String dischargeCondition;
    private Integer stayDays;
    private Integer restDays;
    private String clinicalSummary;
    private String evolutionSummary;
    private String diagnosticFindings;
    private UUID createdBy;

    public static CreatePostOperativeCommand fromRequest(CreatePostOperativeRequest request, UUID createdBy) {
        return new CreatePostOperativeCommand(
                UUID.randomUUID(),
                request.getSurgeryId(),
                request.getTreatmentSummary(),
                request.getDischargeInstructions(),
                request.getLifeStatus(),
                request.getDischargeCondition(),
                request.getStayDays(),
                request.getRestDays(),
                request.getClinicalSummary(),
                request.getEvolutionSummary(),
                request.getDiagnosticFindings(),
                createdBy
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreatePostOperativeMessage(id);
    }
}