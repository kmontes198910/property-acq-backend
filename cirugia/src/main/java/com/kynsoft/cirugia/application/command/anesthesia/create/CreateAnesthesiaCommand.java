package com.kynsoft.cirugia.application.command.anesthesia.create;

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
public class CreateAnesthesiaCommand implements ICommand {
    private String id;
    private UUID surgeryId;
    private String anesthesiaType;
    private String mouthOpening;
    private String thyroMentalDistance;
    private String neckCircumference;
    private String mallampati;
    private String cervicalMobility;
    private String mandibularProtrusion;
    private Boolean difficultIntubationHistory;
    private Boolean intubationDifficulties;
    private String thoraxDescription;
    private String heartDescription;
    private String lungsDescription;
    private String abdomenDescription;
    private String extremitiesDescription;
    private String centralNervousSystem;
    private String asaPhysicalStatus;
    private String operationRisks;
    private String metabolicEquivalent;
    private Integer lastIntoxicationHours;
    private String anesthetics;
    private String surgicalDrugs;
    private String allergies;
    private String transfusions;
    private String habits;
    private UUID createdBy;

    public static CreateAnesthesiaCommand fromRequest(CreateAnesthesiaRequest request, UUID createdBy) {
        return new CreateAnesthesiaCommand(
                UUID.randomUUID().toString(),
                request.getSurgeryId(),
                request.getAnesthesiaType(),
                request.getMouthOpening(),
                request.getThyroMentalDistance(),
                request.getNeckCircumference(),
                request.getMallampati(),
                request.getCervicalMobility(),
                request.getMandibularProtrusion(),
                request.getDifficultIntubationHistory(),
                request.getIntubationDifficulties(),
                request.getThoraxDescription(),
                request.getHeartDescription(),
                request.getLungsDescription(),
                request.getAbdomenDescription(),
                request.getExtremitiesDescription(),
                request.getCentralNervousSystem(),
                request.getAsaPhysicalStatus(),
                request.getOperationRisks(),
                request.getMetabolicEquivalent(),
                request.getLastIntoxicationHours(),
                request.getAnesthetics(),
                request.getSurgicalDrugs(),
                request.getAllergies(),
                request.getTransfusions(),
                request.getHabits(),
                createdBy
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateAnesthesiaMessage(id);
    }
}