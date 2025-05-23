package com.kynsoft.medicaltest.application.command.labtestparameter.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.medicaltest.domain.dto.LabTestParameterDto;
import com.kynsoft.medicaltest.domain.service.ILabTestParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Manejador del comando para actualizar un parámetro de examen de laboratorio
 */
@Component
@RequiredArgsConstructor
public class UpdateLabTestParameterCommandHandler implements ICommandHandler<UpdateLabTestParameterCommand> {

    private final ILabTestParameterService labTestParameterService;

    @Override
    public void handle(UpdateLabTestParameterCommand command) {
        // Obtenemos el DTO actual para mantener los campos que no se actualizan
        LabTestParameterDto existingDto = labTestParameterService.getById(command.getId());

        // Actualizamos solo los campos que pueden cambiar
        existingDto.setName(command.getName());
        existingDto.setUnit(command.getUnit());
        existingDto.setReferenceRangeMin(command.getReferenceRangeMin());
        existingDto.setReferenceRangeMax(command.getReferenceRangeMax());
        existingDto.setGenderSpecific(command.getGenderSpecific());

        LabTestParameterDto updatedDto = labTestParameterService.update(command.getId(), existingDto, command.getUserId());

    }
}
