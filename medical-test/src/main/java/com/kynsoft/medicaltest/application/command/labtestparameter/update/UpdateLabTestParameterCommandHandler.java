package com.kynsoft.medicaltest.application.command.labtestparameter.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.medicaltest.domain.dto.LabTestDto;
import com.kynsoft.medicaltest.domain.dto.LabTestParameterDto;
import com.kynsoft.medicaltest.domain.service.ILabTestParameterService;
import com.kynsoft.medicaltest.domain.service.ILabTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Manejador del comando para actualizar un parámetro de examen de laboratorio
 */
@Component
@RequiredArgsConstructor
public class UpdateLabTestParameterCommandHandler implements ICommandHandler<UpdateLabTestParameterCommand> {

    private final ILabTestParameterService labTestParameterService;
    private final ILabTestService    labTestService;

    @Override
    public void handle(UpdateLabTestParameterCommand command) {
        // Obtenemos el DTO actual para mantener los campos que no se actualizan
        LabTestParameterDto existingDto = labTestParameterService.getById(command.getId());
        Optional<LabTestDto> labTestDto = labTestService.findById(command.getLabTestId());
        labTestDto.ifPresent(existingDto::setLabTest);
        // Actualizamos solo los campos que pueden cambiar
        existingDto.setCode(command.getCode());
        existingDto.setName(command.getName());

        LabTestParameterDto updatedDto = labTestParameterService.update(command.getId(), existingDto, command.getUserId());

    }
}
