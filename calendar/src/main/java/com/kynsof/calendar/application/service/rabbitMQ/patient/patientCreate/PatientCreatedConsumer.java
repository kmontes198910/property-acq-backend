package com.kynsof.calendar.application.service.rabbitMQ.patient.patientCreate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.calendar.domain.dto.PatientDto;
import com.kynsof.calendar.domain.dto.enumType.PatientStatus;
import com.kynsof.calendar.domain.service.IPatientsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PatientCreatedConsumer {

    private final ObjectMapper objectMapper;
    private final IPatientsService patientsService;

    public PatientCreatedConsumer(ObjectMapper objectMapper, IPatientsService patientsService) {
        this.objectMapper = objectMapper;
        this.patientsService = patientsService;
    }

//    @RabbitListener(queues = "paciente.calendar")  // Cola quemada directamente
    public void receiveMessage(String message) {
        try {
            System.err.println(message);
            Person person = objectMapper.readValue(message, Person.class);
            PatientDto patientDto = new PatientDto();
            patientDto.setId(UUID.fromString(person.getId()));
            patientDto.setIdentification(person.getIdentificationNumber());
            patientDto.setEmail(person.getEmail());
            patientDto.setName(person.getFirstName());
            patientDto.setStatus(PatientStatus.ACTIVE);
            patientDto.setImage(person.getImage());
            patientDto.setLastName(person.getLastName());
            patientDto.setProfession(person.getProfession());
            patientsService.create(patientDto);
            System.err.println("📥 Evento recibido en CALENDAR: " + person);
        } catch (Exception e) {
            System.err.println("❌ Error procesando el mensaje en CALENDAR: " + e.getMessage());
        }
    }
}