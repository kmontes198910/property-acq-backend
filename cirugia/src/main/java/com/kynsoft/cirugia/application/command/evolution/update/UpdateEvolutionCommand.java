package com.kynsoft.cirugia.application.command.evolution.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UpdateEvolutionCommand implements ICommand {
    private UUID id;
    private String therapeuticFluids;
    private String prescriptionFluids;
    private String generalCare;
    private String monitoring;
    private String diet;
    private String analytics;
    private String others;
    private String process;
    private UUID updatedBy;
    
    public static UpdateEvolutionCommand fromRequest(UpdateEvolutionRequest request, UUID id, String userId) {
        UpdateEvolutionCommand command = new UpdateEvolutionCommand();
        command.setId(id);
        command.setTherapeuticFluids(request.getTherapeuticFluids());
        command.setPrescriptionFluids(request.getPrescriptionFluids());
        command.setGeneralCare(request.getGeneralCare());
        command.setMonitoring(request.getMonitoring());
        command.setDiet(request.getDiet());
        command.setAnalytics(request.getAnalytics());
        command.setOthers(request.getOthers());
        command.setProcess(request.getProcess());
        command.setUpdatedBy(UUID.fromString(userId));
        return command;
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new UpdateEvolutionMessage(id);
    }
}