package com.kynsoft.cirugia.application.command.intraOperative.create;


import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.cirugia.application.command.postOperative.create.CreatePostOperativeCommand;
import com.kynsoft.cirugia.application.command.postOperative.create.CreatePostOperativeRequest;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
public class CreateIntraOperativeCommand implements ICommand {
    private UUID id;
    private UUID surgeryId;
    private LocalDate date;
    private LocalTime startTime;
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
    private UUID createdBy;

    public static CreateIntraOperativeCommand fromRequest(CreateIntraOperativeRequest request, UUID createdBy) {
        return new CreateIntraOperativeCommand(
               UUID.randomUUID(),
                request.getSurgeryId(),
                LocalDate.now(),
                LocalTime.now(), // Corregido para capturar la hora actual
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
                createdBy
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateIntraOperativeMessage(id);
    }
}