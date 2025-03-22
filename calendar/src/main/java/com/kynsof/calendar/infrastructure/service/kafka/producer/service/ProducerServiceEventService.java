//package com.kynsof.calendar.infrastructure.service.kafka.producer.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.share.core.domain.kafka.entity.Servicekafka;
//import com.kynsof.share.core.domain.kafka.event.CreateEvent;
//import com.kynsof.share.core.domain.kafka.event.EventType;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ProducerServiceEventService {
//
//    private final KafkaTemplate<String, String> producer;
//
//    public ProducerServiceEventService(KafkaTemplate<String, String> producer) {
//        this.producer = producer;
//    }
//
//    public void create(Servicekafka entity) {
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            String json = objectMapper.writeValueAsString(new CreateEvent<>(entity, EventType.CREATED));
//            this.producer.send("medic_service", json);
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ProducerServiceEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
