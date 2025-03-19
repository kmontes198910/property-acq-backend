package com.kynsof.patients.infrastructure.services.kafka.producer.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.share.core.domain.kafka.entity.CustomerKafka;
import com.kynsof.share.core.domain.kafka.event.CreateEvent;
import com.kynsof.share.core.domain.kafka.event.EventType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ProducerCreateCustomerEventService {
    private final KafkaTemplate<String, String> producer;
    private final ObjectMapper objectMapper;
    public ProducerCreateCustomerEventService(KafkaTemplate<String, String> producer, ObjectMapper objectMapper) {
        this.producer = producer;
        this.objectMapper = objectMapper;
    }

    public void create(CustomerKafka entity) {

        try {

            //ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(new CreateEvent<>(entity, EventType.CREATED));

            this.producer.send(topic, json);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ProducerCreateCustomerEventService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}