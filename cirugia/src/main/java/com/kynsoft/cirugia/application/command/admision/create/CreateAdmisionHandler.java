package com.kynsoft.cirugia.application.command.admision.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.cirugia.domain.dto.AdmisionDto;
import com.kynsoft.cirugia.domain.service.IAdmisionService;
import com.kynsoft.cirugia.domain.service.ISurgeryService;
import com.kynsoft.cirugia.infrastructure.entities.SurgeryEntity;
import com.kynsoft.cirugia.infrastructure.repository.query.SurgeryReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Handler for the admission creation command
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CreateAdmisionHandler implements ICommandHandler<CreateAdmisionCommand> {

    private final IAdmisionService admisionService;
    private final ISurgeryService surgeryService;
    private final SurgeryReadRepository surgeryReadRepository;

    @Override
    public void handle(CreateAdmisionCommand command) throws BusinessException {
        log.info("Processing admission creation command for surgery with ID: {}", command.getSurgeryId());
        
        // Verify that the surgery exists
        Optional<SurgeryEntity> surgeryOpt = surgeryService.getSurgeryEntityById(command.getSurgeryId());
        if (surgeryOpt.isEmpty()) {
            throw new BusinessNotFoundException(
                new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, 
                new ErrorField("surgeryId", "Surgery not found with ID: " + command.getSurgeryId())));
        }

        
        // Create admission DTO
        AdmisionDto admisionDto = new AdmisionDto();
        admisionDto.setId(command.getId() != null ? command.getId() : UUID.randomUUID());
        admisionDto.setRoom(command.getRoom());
        admisionDto.setBed(command.getBed());
        admisionDto.setObservations(command.getObservations());
        admisionDto.setSurgeryId(command.getSurgeryId());
        admisionDto.setCreatedBy(command.getCreatedBy());
        
        // Create admission
        UUID admisionId = admisionService.createAdmision(admisionDto);
        command.setId(admisionId);
        
        log.info("Admission successfully created with ID: {}", admisionId);
    }
}
