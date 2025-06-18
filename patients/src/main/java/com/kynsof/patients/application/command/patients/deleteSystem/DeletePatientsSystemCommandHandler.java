package com.kynsof.patients.application.command.patients.deleteSystem;


import com.kynsof.patients.domain.dto.PatientDto;
import com.kynsof.patients.infrastructure.services.PatientsServiceImpl;
import com.kynsof.patients.infrastructure.services.rabbitMQ.dto.RabbitMQPatientDeleteDto;
import com.kynsof.patients.infrastructure.services.rabbitMQ.eventPublisher.EventPatientDeletePublisherService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeletePatientsSystemCommandHandler  implements ICommandHandler<DeletePatientsSystemCommand> {

    private final PatientsServiceImpl serviceImpl;
    private final EventPatientDeletePublisherService patientDeletePublisherService;

    public DeletePatientsSystemCommandHandler(PatientsServiceImpl serviceImpl, EventPatientDeletePublisherService patientDeletePublisherService) {
        this.serviceImpl = serviceImpl;
        this.patientDeletePublisherService = patientDeletePublisherService;
    }

    @Override
    public void handle(DeletePatientsSystemCommand command) {
        PatientDto delete = this.serviceImpl.findByIdSimple(command.getId());

        serviceImpl.deleteSystem(delete);
        patientDeletePublisherService.publishEvent(new RabbitMQPatientDeleteDto(
                delete.getId(),
                delete.getIdentification()
        ));

    }

}