package com.kynsof.treatments.application.service.rabbitMQ.patientUpdate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.treatments.application.service.rabbitMQ.patientCreate.Person;
import com.kynsof.treatments.domain.dto.PatientDto;
import com.kynsof.treatments.domain.dto.enumDto.Status;
import com.kynsof.treatments.domain.service.IPatientsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RabbitMQPatientUpdateConsumer {

    private final ObjectMapper objectMapper;
    private final IPatientsService patientsService;

    public RabbitMQPatientUpdateConsumer(ObjectMapper objectMapper, IPatientsService patientsService) {
        this.objectMapper = objectMapper;
        this.patientsService = patientsService;
    }

    @RabbitListener(queues = "paciente.update.treatment")
    public void receiveMessage(String message) {
        try {
            System.err.println("📥 Evento recibido en TREATMENT: " + message);
            Person person = objectMapper.readValue(message, Person.class);
            PatientDto patientDto = new PatientDto(UUID.fromString(person.getId()),person.getIdentificationNumber(), person.getFirstName(),
                    person.getLastName(), Status.ACTIVE,person.getBirthDate(), person.getProfession());
            patientsService.update(patientDto);
            System.err.println("📥 Evento recibido en TREATMENT: " + person);
        } catch (Exception e) {
            System.err.println("❌ Error procesando el mensaje en TREATMENT: " + e.getMessage());
        }
    }
}