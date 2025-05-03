package com.kynsoft.cirugia.application.command.intraOperative.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.dto.IntraOperative;
import com.kynsoft.cirugia.domain.service.IIntraOperativeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class UpdateIntraOperativeCommandHandler implements ICommandHandler<UpdateIntraOperativeCommand> {

    private final IIntraOperativeRepository intraOperativeRepository;

    public UpdateIntraOperativeCommandHandler(IIntraOperativeRepository intraOperativeRepository) {
        this.intraOperativeRepository = intraOperativeRepository;
    }

    @Override
    public void handle(UpdateIntraOperativeCommand command) {
        log.info("Updating IntraOperative with ID: {}", command.getId());
        
        Optional<IntraOperative> optionalIntraOperative = intraOperativeRepository.findById(command.getId().toString());
        
        if (optionalIntraOperative.isPresent()) {
            IntraOperative intraOperative = optionalIntraOperative.get();
            
            // Actualizar los campos
            intraOperative.setSurgeryId(command.getSurgeryId());
            intraOperative.setDate(command.getDate());
            intraOperative.setStartTime(command.getStartTime());
            intraOperative.setEndTime(command.getEndTime());
            intraOperative.setProcedureType(command.getProcedureType());
            intraOperative.setAnesthesiaType(command.getAnesthesiaType());
            intraOperative.setProjectedProcedure(command.getProjectedProcedure());
            intraOperative.setPerformedProcedure(command.getPerformedProcedure());
            intraOperative.setDieresis(command.getDieresis());
            intraOperative.setExpositionExploration(command.getExpositionExploration());
            intraOperative.setSurgicalFindings(command.getSurgicalFindings());
            intraOperative.setBloodLoss(command.getBloodLoss());
            intraOperative.setApproximateBlood(command.getApproximateBlood());
            intraOperative.setProstheticMaterial(command.getProstheticMaterial());
            intraOperative.setSurgicalProcedure(command.getSurgicalProcedure());
            intraOperative.setDescription(command.getDescription());
            intraOperative.setUpdatedAt(LocalDateTime.now());
            intraOperative.setUpdatedBy(command.getUpdatedBy());
            
            intraOperativeRepository.update(intraOperative);
            log.info("IntraOperative updated successfully");
        } else {
            log.error("IntraOperative with ID {} not found", command.getId());
            throw new IllegalArgumentException("IntraOperative not found with ID: " + command.getId());
        }
    }
}