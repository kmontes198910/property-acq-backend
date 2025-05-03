package com.kynsoft.cirugia.application.command.postOperative.update;

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
public class UpdatePostOperativeRequest  {
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
}