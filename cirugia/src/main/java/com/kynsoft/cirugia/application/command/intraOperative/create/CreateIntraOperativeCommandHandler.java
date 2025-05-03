package com.kynsoft.cirugia.application.command.intraOperative.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.dto.IntraOperative;
import com.kynsoft.cirugia.domain.service.IIntraOperativeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class CreateIntraOperativeCommandHandler implements ICommandHandler<CreateIntraOperativeCommand> {

    private final IIntraOperativeRepository intraOperativeRepository;

    public CreateIntraOperativeCommandHandler(IIntraOperativeRepository intraOperativeRepository) {
        this.intraOperativeRepository = intraOperativeRepository;
    }

    @Override
    public void handle(CreateIntraOperativeCommand command) {
        log.info("Creating IntraOperative for surgery: {}", command.getSurgeryId());
        IntraOperative intraOperative = IntraOperative.builder()
                .id(command.getId())
                .surgeryId(command.getSurgeryId())
                .date(command.getDate())
                .startTime(command.getStartTime())
                .endTime(command.getEndTime())
                .procedureType(command.getProcedureType())
                .anesthesiaType(command.getAnesthesiaType())
                .projectedProcedure(command.getProjectedProcedure())
                .performedProcedure(command.getPerformedProcedure())
                .dieresis(command.getDieresis())
                .expositionExploration(command.getExpositionExploration())
                .surgicalFindings(command.getSurgicalFindings())
                .bloodLoss(command.getBloodLoss())
                .approximateBlood(command.getApproximateBlood())
                .prostheticMaterial(command.getProstheticMaterial())
                .surgicalProcedure(command.getSurgicalProcedure())
                .description(command.getDescription())
                .createdAt(LocalDateTime.now())
                .createdBy(command.getCreatedBy())
                .build();

        IntraOperative result = intraOperativeRepository.create(intraOperative);
        command.setId(result.getId());
        log.info("IntraOperative created with ID: {}", intraOperative.getId());
    }
}