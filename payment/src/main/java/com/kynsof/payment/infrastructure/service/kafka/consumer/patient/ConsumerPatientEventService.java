package com.kynsof.payment.infrastructure.service.kafka.consumer.patient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.payment.domain.dto.ClientDto;
import com.kynsof.payment.domain.dto.enumDto.Status;
import com.kynsof.payment.domain.service.IClientService;
import com.kynsof.share.core.domain.kafka.entity.CustomerKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ConsumerPatientEventService {

    private final IClientService service;
    private final ObjectMapper objectMapper;

    public ConsumerPatientEventService(IClientService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "medinec-create-patient", groupId = "treatments-payment")
    public void listen(String event) {
        try {

            JsonNode rootNode = objectMapper.readTree(event);

            CustomerKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), CustomerKafka.class);

            ClientDto clientDto = new ClientDto(UUID.fromString(eventRead.getId()),
                    eventRead.getIdentificationNumber(), 
                    eventRead.getFirstName(), 
                    eventRead.getLastName(), 
                    Status.ACTIVE,
                    eventRead.getEmail(),
                    eventRead.getPhoneNumber()
            );
            this.service.create(clientDto);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(ConsumerPatientEventService.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

}
