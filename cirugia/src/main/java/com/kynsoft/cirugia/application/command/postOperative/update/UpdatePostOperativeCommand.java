package com.kynsoft.cirugia.application.command.postOperative.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.cirugia.application.command.postOperative.create.CreatePostOperativeCommand;
import com.kynsoft.cirugia.application.command.postOperative.create.CreatePostOperativeRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostOperativeCommand implements ICommand {
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
    private UUID updatedBy;

    public static UpdatePostOperativeCommand fromRequest(UUID id,UpdatePostOperativeRequest request, UUID createdBy) {
        return new UpdatePostOperativeCommand(
                id,
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
        return new UpdatePostOperativeMessage(id);
    }
}