package com.kynsof.evaluation.infrastructure.service.kafka.consumer.patient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.share.core.domain.kafka.entity.CustomerKafka;
import com.kynsof.evaluation.domain.dto.PatientDto;
import com.kynsof.evaluation.domain.dto.enumDto.Status;
import com.kynsof.evaluation.domain.service.IPatientsService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ConsumerPatientEventService {

    private final IPatientsService service;
    private final ObjectMapper objectMapper;
    public ConsumerPatientEventService(IPatientsService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "medinec-create-patient", groupId = "treatments-patient")
    public void listen(String event) {
        try {

            JsonNode rootNode = objectMapper.readTree(event);

            CustomerKafka eventRead = objectMapper.treeToValue(rootNode.get("data"),
                    CustomerKafka.class);

            PatientDto patientDto = new PatientDto(
                    UUID.fromString(eventRead.getId()),
                    eventRead.getIdentificationNumber(),
                    eventRead.getFirstName(),
                    eventRead.getLastName(),
                    Status.ACTIVE,
                    eventRead.getBirthDate()
            );
            patientDto.setGender(eventRead.getGender());
            this.service.create(patientDto);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(ConsumerPatientEventService.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

}
