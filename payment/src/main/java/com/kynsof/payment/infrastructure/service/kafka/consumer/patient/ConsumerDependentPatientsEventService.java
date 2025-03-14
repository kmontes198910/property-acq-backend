package com.kynsof.payment.infrastructure.service.kafka.consumer.patient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.payment.domain.dto.ClientDto;
import com.kynsof.payment.domain.dto.enumDto.Status;
import com.kynsof.payment.domain.service.IClientService;
import com.kynsof.share.core.domain.kafka.entity.UserKafka;
import com.kynsof.share.core.domain.kafka.event.EventType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ConsumerDependentPatientsEventService {

    private final IClientService service;

    public ConsumerDependentPatientsEventService(IClientService service) {
        this.service = service;
    }

    // Ejemplo de un método listener
    @KafkaListener(topics = "user-dependent", groupId = "user-dependent-payment")
    public void listen(String event) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(event);

            UserKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), UserKafka.class);
            EventType eventType = objectMapper.treeToValue(rootNode.get("type"), EventType.class);
            if (eventType.equals(EventType.CREATED)) {
                //Definir accion
                this.service.create(new ClientDto(
                        UUID.fromString(eventRead.getId()),
                        eventRead.getIdentification(),
                        eventRead.getFirstname(),
                        eventRead.getLastname(),
                        Status.ACTIVE,
                        eventRead.getEmail(),
                        eventRead.getPhone()
                ));
            }
            if (eventType.equals(EventType.UPDATED)) {
                //Definir accion
                this.service.update(new ClientDto(
                        UUID.fromString(eventRead.getId()),
                        eventRead.getIdentification(),
                        eventRead.getFirstname(),
                        eventRead.getLastname(),
                        Status.ACTIVE,
                        eventRead.getEmail(),
                        eventRead.getPhone()
                ));
            }
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ConsumerDependentPatientsEventService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
