package com.kynsoft.medicaltest.application.command.labtestparameter.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.medicaltest.domain.dto.LabTestParameterDto;
import com.kynsoft.medicaltest.domain.service.ILabTestParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Manejador del comando para crear un nuevo parámetro de examen de laboratorio
 */
@Component
@RequiredArgsConstructor
public class CreateLabTestParameterCommandHandler implements ICommandHandler<CreateLabTestParameterCommand> {

    private final ILabTestParameterService labTestParameterService;

    @Override
    public void handle(CreateLabTestParameterCommand command) {
        LabTestParameterDto dto = LabTestParameterDto.builder()
                .labTestId(command.getLabTestId())
                .name(command.getName())
                .unit(command.getUnit())
                .referenceRangeMin(command.getReferenceRangeMin())
                .referenceRangeMax(command.getReferenceRangeMax())
                .genderSpecific(command.getGenderSpecific())
                .build();

        LabTestParameterDto createdDto = labTestParameterService.create(dto, command.getUserId());

        // Aquí puedes manejar la respuesta o el mensaje de éxito
        command.setId(createdDto.getId());
    }
}
