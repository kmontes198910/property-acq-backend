package com.kynsof.payment.infrastructure.service.kafka.consumer.patient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.payment.domain.dto.ClientDto;
import com.kynsof.payment.domain.dto.enumDto.Status;
import com.kynsof.payment.domain.service.IClientService;
import com.kynsof.share.core.domain.kafka.entity.PatientKafka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ConsumerPatientUpdateEventService {

    @Autowired
    private IClientService service;

    @KafkaListener(topics = "patient-update", groupId = "treatments-payment")
    public void listen(String event) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(event);

            PatientKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), PatientKafka.class);

            ClientDto clientDto = new ClientDto(UUID.fromString(eventRead.getId()),
                    eventRead.getIdentification(), 
                    eventRead.getName(), 
                    eventRead.getLastName(), 
                    Status.ACTIVE,
                    "info@gmail.com",
                    "0983825630"
            );
            this.service.create(clientDto);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(ConsumerPatientUpdateEventService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
