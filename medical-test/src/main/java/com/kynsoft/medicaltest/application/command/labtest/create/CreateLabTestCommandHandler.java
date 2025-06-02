package com.kynsoft.medicaltest.application.command.labtest.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.medicaltest.domain.dto.LabTestDto;
import com.kynsoft.medicaltest.domain.dto.LabTestParameterDto;
import com.kynsoft.medicaltest.domain.service.ILabTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Manejador para el comando de creación de examen de laboratorio
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CreateLabTestCommandHandler implements ICommandHandler<CreateLabTestCommand> {

    private final ILabTestService labTestService;

    @Override
    public void handle(CreateLabTestCommand command) {
        log.info("Creando nuevo examen de laboratorio: {}", command.getName());
        
        // Crear DTO a partir del comando
        LabTestDto labTestDto = LabTestDto.builder()
                .id(command.getId())
                .code(command.getCode())
                .name(command.getName())
                .description(command.getDescription())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(command.getCreatedBy())
                .updatedBy(command.getCreatedBy())
                .build();
        // Guardar a través del servicio
        LabTestDto testDto=  labTestService.create(labTestDto);
        command.setId(testDto.getId());
    }
}
