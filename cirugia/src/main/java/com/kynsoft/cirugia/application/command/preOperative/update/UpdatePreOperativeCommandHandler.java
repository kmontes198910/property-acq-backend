package com.kynsoft.cirugia.application.command.preOperative.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.dto.PreOperative;
import com.kynsoft.cirugia.domain.service.IPreOperativeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class UpdatePreOperativeCommandHandler implements ICommandHandler<UpdatePreOperativeCommand> {

    private final IPreOperativeRepository preOperativeRepository;

    public UpdatePreOperativeCommandHandler(IPreOperativeRepository preOperativeRepository) {
        this.preOperativeRepository = preOperativeRepository;
    }

    @Override
    public void handle(UpdatePreOperativeCommand command) {
        log.info("Updating PreOperative with ID: {}", command.getId());
        
        Optional<PreOperative> optionalPreOperative = preOperativeRepository.findById(command.getId().toString());
        
        if (optionalPreOperative.isPresent()) {
            PreOperative preOperative = optionalPreOperative.get();
            
            // Actualizar los campos
            preOperative.setSurgeryId(command.getSurgeryId());
            preOperative.setAdmissionReason(command.getAdmissionReason());
            preOperative.setCurrentDiseaseHistory(command.getCurrentDiseaseHistory());
            preOperative.setPhysicalExamination(command.getPhysicalExamination());
            preOperative.setUpdatedAt(LocalDateTime.now());
            preOperative.setUpdatedBy(command.getUpdatedBy());
            
            preOperativeRepository.create(preOperative);
            log.info("PreOperative updated successfully");
        } else {
            log.error("PreOperative with ID {} not found", command.getId());
            throw new IllegalArgumentException("PreOperative not found with ID: " + command.getId());
        }
    }
}