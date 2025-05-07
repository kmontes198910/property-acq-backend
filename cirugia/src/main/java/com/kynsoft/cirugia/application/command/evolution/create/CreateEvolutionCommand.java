package com.kynsoft.cirugia.application.command.evolution.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CreateEvolutionCommand implements ICommand {
    private UUID id;
    private UUID surgeryId;
    private String therapeuticFluids;
    private String prescriptionFluids;
    private String generalCare;
    private String monitoring;
    private String diet;
    private String analytics;
    private String others;
    private String process;
    private UUID createdBy;
    
    public CreateEvolutionCommand() {
        this.id = UUID.randomUUID();
    }
    
    public static CreateEvolutionCommand fromRequest(CreateEvolutionRequest request, String userId) {
        CreateEvolutionCommand command = new CreateEvolutionCommand();
        command.setSurgeryId(request.getSurgeryId());
        command.setTherapeuticFluids(request.getTherapeuticFluids());
        command.setPrescriptionFluids(request.getPrescriptionFluids());
        command.setGeneralCare(request.getGeneralCare());
        command.setMonitoring(request.getMonitoring());
        command.setDiet(request.getDiet());
        command.setAnalytics(request.getAnalytics());
        command.setOthers(request.getOthers());
        command.setProcess(request.getProcess());
        command.setCreatedBy(UUID.fromString(userId));
        return command;
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new CreateEvolutionMessage(id);
    }
}