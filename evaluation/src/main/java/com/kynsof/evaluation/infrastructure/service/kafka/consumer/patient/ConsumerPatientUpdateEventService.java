package com.kynsof.evaluation.infrastructure.service.kafka.consumer.patient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.share.core.domain.kafka.entity.PatientKafka;
import com.kynsof.share.core.domain.kafka.event.EventType;
import com.kynsof.evaluation.domain.dto.PatientDto;
import com.kynsof.evaluation.domain.dto.enumDto.Status;
import com.kynsof.evaluation.domain.service.IPatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ConsumerPatientUpdateEventService {

    @Autowired
    private IPatientsService service;

    @KafkaListener(topics = "patient-update", groupId = "evaluation-patient")
    public void listen(String event) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(event);

            PatientKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), PatientKafka.class);
            EventType eventType = objectMapper.treeToValue(rootNode.get("type"), EventType.class);

            this.service.update(new PatientDto(
                    UUID.fromString(eventRead.getId()),
                    eventRead.getIdentification(),
                    eventRead.getName(),
                    eventRead.getLastName(),
                    eventRead.getGender(),
                    Status.ACTIVE,
                    LocalDate.parse(eventRead.getBirthdayDate())
            ));

        } catch (JsonProcessingException ex) {
            Logger.getLogger(ConsumerPatientUpdateEventService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
