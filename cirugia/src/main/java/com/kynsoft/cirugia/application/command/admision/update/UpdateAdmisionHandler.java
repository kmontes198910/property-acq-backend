package com.kynsoft.cirugia.application.command.admision.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.cirugia.domain.dto.AdmisionDto;
import com.kynsoft.cirugia.domain.service.IAdmisionService;
import com.kynsoft.cirugia.infrastructure.entities.AdmisionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Handler for the admission update command
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateAdmisionHandler implements ICommandHandler<UpdateAdmisionCommand> {

    private final IAdmisionService admisionService;

    @Override
    public void handle(UpdateAdmisionCommand command) throws BusinessException {
        log.info("Processing admission update command with ID: {}", command.getId());
        
        // Verify that the admission exists
        Optional<AdmisionDto> admisionOpt = admisionService.getAdmisionById(command.getId());
        if (admisionOpt.isEmpty()) {
            throw new BusinessNotFoundException(
                new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, 
                new ErrorField("id", "Admission not found with ID: " + command.getId())));
        }
        
        // Create DTO for the update
        AdmisionDto admisionDto = new AdmisionDto();
        admisionDto.setId(command.getId());
        admisionDto.setRoom(command.getRoom());
        admisionDto.setBed(command.getBed());
        admisionDto.setObservations(command.getObservations());
        admisionDto.setUpdatedBy(command.getUpdatedBy());
        
        // Update the admission
        admisionService.updateAdmision(admisionDto);
        
        log.info("Admission successfully updated with ID: {}", command.getId());
    }
}
