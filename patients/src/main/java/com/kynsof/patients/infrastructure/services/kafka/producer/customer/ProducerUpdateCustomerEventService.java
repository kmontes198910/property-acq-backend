//package com.kynsof.patients.infrastructure.services.kafka.producer.customer;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.share.core.domain.kafka.entity.UpdateCustomerKafka;
//import com.kynsof.share.core.domain.kafka.event.CreateEvent;
//import com.kynsof.share.core.domain.kafka.event.EventType;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ProducerUpdateCustomerEventService {
//    private final KafkaTemplate<String, String> producer;
//
//    public ProducerUpdateCustomerEventService(KafkaTemplate<String, String> producer) {
//        this.producer = producer;
//    }
//
//    public void update(UpdateCustomerKafka entity) {
//
//        try {
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            String json = objectMapper.writeValueAsString(new CreateEvent<>(entity, EventType.CREATED));
//
//            this.producer.send("update-custumer", json);
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ProducerUpdateCustomerEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}