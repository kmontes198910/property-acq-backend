package com.kynsoft.cirugia.application.command.anesthesia.create;

import com.kynsoft.cirugia.domain.dto.Anesthesia;
import com.kynsoft.cirugia.domain.service.IAnesthesiaService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateAnesthesiaCommandHandler implements ICommandHandler<CreateAnesthesiaCommand> {

    private final IAnesthesiaService anesthesiaService;
    @Override
    public void handle(CreateAnesthesiaCommand command) {
        Anesthesia anesthesia = Anesthesia.builder()
                .id(UUID.fromString(command.getId()))
                .surgeryId(command.getSurgeryId())
                .anesthesiaType(command.getAnesthesiaType())
                .mouthOpening(command.getMouthOpening())
                .thyroMentalDistance(command.getThyroMentalDistance())
                .neckCircumference(command.getNeckCircumference())
                .mallampati(command.getMallampati())
                .cervicalMobility(command.getCervicalMobility())
                .mandibularProtrusion(command.getMandibularProtrusion())
                .difficultIntubationHistory(command.getDifficultIntubationHistory())
                .intubationDifficulties(command.getIntubationDifficulties())
                .thoraxDescription(command.getThoraxDescription())
                .heartDescription(command.getHeartDescription())
                .lungsDescription(command.getLungsDescription())
                .abdomenDescription(command.getAbdomenDescription())
                .extremitiesDescription(command.getExtremitiesDescription())
                .centralNervousSystem(command.getCentralNervousSystem())
                .asaPhysicalStatus(command.getAsaPhysicalStatus())
                .operationRisks(command.getOperationRisks())
                .metabolicEquivalent(command.getMetabolicEquivalent())
                .lastIntoxicationHours(command.getLastIntoxicationHours())
                .anesthetics(command.getAnesthetics())
                .surgicalDrugs(command.getSurgicalDrugs())
                .allergies(command.getAllergies())
                .transfusions(command.getTransfusions())
                .habits(command.getHabits())
                .createdAt(LocalDateTime.now())
                .createdBy(command.getCreatedBy())
                .build();

        anesthesiaService.createAnesthesia(anesthesia);

    }
}