package com.kynsof.payment.infrastructure.service.kafka.consumer.create.doctor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.payment.domain.dto.ClientDto;
import com.kynsof.payment.domain.dto.enumDto.Status;
import com.kynsof.payment.domain.service.IClientService;
import com.kynsof.share.core.domain.kafka.entity.DoctorKafka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ConsumerCreateDoctorEventService {

    @Autowired
    private IClientService service;

    // Ejemplo de un método listener
    @KafkaListener(topics = "medinec-replicate-doctor", groupId = "payment")
    public void listen(String event) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(event);
            DoctorKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), DoctorKafka.class);

            this.service.create(new ClientDto(
                    eventRead.getId(),
                    eventRead.getIdentification(),
                    eventRead.getName(),
                    eventRead.getLastName(),
                    Status.ACTIVE,
                    eventRead.getEmail(),
                    "0983825630"
            ));

        } catch (JsonProcessingException ex) {
            Logger.getLogger(ConsumerCreateDoctorEventService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
