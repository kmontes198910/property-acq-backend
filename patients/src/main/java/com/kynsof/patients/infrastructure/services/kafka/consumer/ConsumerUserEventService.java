package com.kynsof.patients.infrastructure.services.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.patients.domain.dto.ContactInfoDto;
import com.kynsof.patients.domain.dto.PatientDto;
import com.kynsof.patients.domain.dto.enumTye.GenderType;
import com.kynsof.patients.domain.dto.enumTye.Status;
import com.kynsof.patients.domain.service.IContactInfoService;
import com.kynsof.patients.domain.service.IPatientsService;
import com.kynsof.share.core.domain.kafka.entity.UserKafka;
import com.kynsof.share.core.domain.kafka.event.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ConsumerUserEventService {

    private final IPatientsService service;

    private final IContactInfoService contactInfoService;

    public ConsumerUserEventService(IPatientsService service, IContactInfoService contactInfoService) {
        this.service = service;
        this.contactInfoService = contactInfoService;
    }

    // Ejemplo de un método listener
    @KafkaListener(topics = "user", groupId = "patient-user")
    public void listen(String event) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(event);

            UserKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), UserKafka.class);
            EventType eventType = objectMapper.treeToValue(rootNode.get("type"), EventType.class);

            if (eventType.equals(EventType.CREATED)) {
                //Definir accion
                UUID patientId = this.service.create(new PatientDto(UUID.fromString(eventRead.getId()),
                        eventRead.getIdentification(), eventRead.getFirstname(), eventRead.getLastname(), GenderType.UNDEFINED, Status.ACTIVE,
                       null, null, null, null, 0));

                PatientDto patientDto = this.service.findByIdSimple(patientId);
                this.contactInfoService.create(new ContactInfoDto(UUID.randomUUID(), patientDto, eventRead.getEmail(),
                        eventRead.getPhone(), null, null, Status.ACTIVE, null
                ));

            }
            if (eventType.equals(EventType.DELETED)) {
                //Definir accion
                System.err.println("#######################################################");
                System.err.println("#######################################################");
                System.err.println("SE EJECUTA UN DELETED");
                System.err.println("#######################################################");
                System.err.println("#######################################################");

            }
            if (eventType.equals(EventType.UPDATED)) {
                //Definir accion
                System.err.println("#######################################################");
                System.err.println("#######################################################");
                System.err.println("SE EJECUTA UN UPDATED");
                System.err.println("#######################################################");
                System.err.println("#######################################################");
                this.service.update(new PatientDto(UUID.fromString(eventRead.getId()),
                        "", eventRead.getFirstname(), eventRead.getLastname(), GenderType.OTHER, Status.ACTIVE,
                        null, null, null, null, 0));
            }

        } catch (JsonProcessingException ex) {
            Logger.getLogger(ConsumerUserEventService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
