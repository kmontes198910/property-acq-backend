package com.kynsoft.cirugia.application.command.intraOperative.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
public class UpdateIntraOperativeCommand implements ICommand {
    private UUID id;
    private UUID surgeryId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String procedureType;
    private String anesthesiaType;
    private String projectedProcedure;
    private String performedProcedure;
    private String dieresis;
    private String expositionExploration;
    private String surgicalFindings;
    private Integer bloodLoss;
    private Integer approximateBlood;
    private Boolean prostheticMaterial;
    private String surgicalProcedure;
    private String description;
    private UUID updatedBy;


    public static UpdateIntraOperativeCommand fromRequest(UpdateIntraOperativeRequest request,UUID id,  UUID updateBy) {
        return new UpdateIntraOperativeCommand(
                id,
                request.getSurgeryId(),
                request.getDate(),
                request.getStartTime(),
                request.getEndTime(),
                request.getProcedureType(),
                request.getAnesthesiaType(),
                request.getProjectedProcedure(),
                request.getPerformedProcedure(),
                request.getDieresis(),
                request.getExpositionExploration(),
                request.getSurgicalFindings(),
                request.getBloodLoss(),
                request.getApproximateBlood(),
                request.getProstheticMaterial(),
                request.getSurgicalProcedure(),
                request.getDescription(),
                updateBy
        );
    }
    @Override
    public ICommandMessage getMessage() {
        return new UpdateIntraOperativeMessage(id);
    }
}