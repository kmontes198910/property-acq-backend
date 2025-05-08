package com.kynsoft.cirugia.application.command.anesthesia.update;

import com.kynsoft.cirugia.domain.dto.Anesthesia;
import com.kynsoft.cirugia.domain.service.IAnesthesiaService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateAnesthesiaCommandHandler implements ICommandHandler<UpdateAnesthesiaCommand> {

    private final IAnesthesiaService anesthesiaService;

    @Override
    public void handle(UpdateAnesthesiaCommand command) {
        // Verificar que la anestesia existe
        Optional<Anesthesia> existingAnesthesiaOpt = anesthesiaService.getAnesthesiaById(command.getId());
        if (existingAnesthesiaOpt.isEmpty()) {
            throw new IllegalArgumentException("No existe una anestesia con el ID: " + command.getId());
        }

        Anesthesia existingAnesthesia = existingAnesthesiaOpt.get();
        
        // Actualizar todos los campos de la anestesia usando sus valores reales
        existingAnesthesia.setSurgeryId(command.getSurgeryId());
        existingAnesthesia.setAnesthesiaType(command.getAnesthesiaType());
        existingAnesthesia.setMouthOpening(command.getMouthOpening());
        existingAnesthesia.setThyromental_distance(command.getThyromental_distance());
        existingAnesthesia.setMallampati(command.getMallampati());
        existingAnesthesia.setCervicalMobility(command.getCervicalMobility());
        existingAnesthesia.setMandibularProtrusion(command.getMandibularProtrusion());
        existingAnesthesia.setDifficultIntubationHistory(command.getDifficultIntubationHistory());
        existingAnesthesia.setIntubationDifficulties(command.getIntubationDifficulties());
        existingAnesthesia.setThoraxDescription(command.getThoraxDescription());
        existingAnesthesia.setHeartDescription(command.getHeartDescription());
        existingAnesthesia.setLungsDescription(command.getLungsDescription());
        existingAnesthesia.setAbdomenDescription(command.getAbdomenDescription());
        existingAnesthesia.setExtremitiesDescription(command.getExtremitiesDescription());
        existingAnesthesia.setCentralNervousSystem(command.getCentralNervousSystem());
        existingAnesthesia.setAsaPhysicalStatus(command.getAsaPhysicalStatus());
        existingAnesthesia.setOperationRisks(command.getOperationRisks());
        existingAnesthesia.setMetabolicEquivalent(command.getMetabolicEquivalent());
        existingAnesthesia.setLastIntoxicationHours(command.getLastIntoxicationHours());
        existingAnesthesia.setAnesthetics(command.getAnesthetics());
        existingAnesthesia.setSurgicalDrugs(command.getSurgicalDrugs());
        existingAnesthesia.setAllergies(command.getAllergies());
        existingAnesthesia.setTransfusions(command.getTransfusions());
        existingAnesthesia.setHabits(command.getHabits());
        
        // Actualizar información de auditoría
        existingAnesthesia.setUpdatedBy(command.getUpdatedBy());
        existingAnesthesia.setUpdatedAt(LocalDateTime.now());
        
        // Guardar los cambios
        anesthesiaService.updateAnesthesia(existingAnesthesia);

    }
}