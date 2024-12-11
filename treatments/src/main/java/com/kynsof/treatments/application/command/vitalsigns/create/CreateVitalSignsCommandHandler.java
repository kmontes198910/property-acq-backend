package com.kynsof.treatments.application.command.vitalsigns.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.PatientDto;
import com.kynsof.treatments.domain.dto.VitalSignsDto;
import com.kynsof.treatments.domain.service.IPatientsService;
import com.kynsof.treatments.domain.service.IVitalSignsService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateVitalSignsCommandHandler implements ICommandHandler<CreateVitalSignsCommand> {

    private final IVitalSignsService vitalSignsService;
    private final IPatientsService patientsService;

    public CreateVitalSignsCommandHandler(IVitalSignsService vitalSignsService, IPatientsService patientsService) {
        this.vitalSignsService = vitalSignsService;
        this.patientsService = patientsService;
    }

    @Override
    public void handle(CreateVitalSignsCommand command) {
        UUID id = UUID.randomUUID();
        PatientDto patientDto = patientsService.findById(command.getPatientId());

        // Construir el DTO con los datos del comando
        VitalSignsDto vitalSignsDto = new VitalSignsDto(
                id,
                command.getBloodPressure(),
                command.getHeartRate(),
                command.getOxygenSaturation(),
                command.getRespiratoryRate(),
                command.getTemperature(),
                patientDto,// Crear relación con paciente
                command.getWeight(),
                command.getHeight(),
                command.getCranialCircumference()
        );

        // Llamar al servicio para crear
        vitalSignsService.create(vitalSignsDto);

        // Establecer el ID en el comando para generar la respuesta
        command.setId(id);
    }
}