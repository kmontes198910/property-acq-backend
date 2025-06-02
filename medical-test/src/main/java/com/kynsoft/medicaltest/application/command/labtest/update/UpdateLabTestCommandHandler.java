package com.kynsoft.medicaltest.application.command.labtest.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.medicaltest.domain.dto.LabTestDto;
import com.kynsoft.medicaltest.domain.dto.LabTestParameterDto;
import com.kynsoft.medicaltest.domain.service.ILabTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Manejador para el comando de actualización de examen de laboratorio
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateLabTestCommandHandler implements ICommandHandler<UpdateLabTestCommand> {

    private final ILabTestService labTestService;

    @Override
    public void handle(UpdateLabTestCommand command) {
        log.info("Actualizando examen de laboratorio con ID: {}", command.getId());
        
        // Obtener el examen existente
        LabTestDto existingLabTest = labTestService.findById(command.getId())
                .orElseThrow(() -> new BusinessNotFoundException(
                        new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND,
                                new ErrorField("id", "Examen de laboratorio no encontrado con ID: " + command.getId()))));
        
        // Actualizar los campos si se proporcionan
        if (command.getCode() != null) {
            existingLabTest.setCode(command.getCode());
        }
        
        if (command.getName() != null) {
            existingLabTest.setName(command.getName());
        }
        
        if (command.getDescription() != null) {
            existingLabTest.setDescription(command.getDescription());
        }

        
        // Actualizar campos de auditoría
        existingLabTest.setUpdatedAt(LocalDateTime.now());
        existingLabTest.setUpdatedBy(command.getUpdatedBy());
        
        // Guardar a través del servicio
        labTestService.update(existingLabTest);
    }
}
