//package com.kynsof.calendar.infrastructure.service.kafka.producer;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.share.core.domain.kafka.entity.UpdateResourceKafka;
//import com.kynsof.share.core.domain.kafka.event.CreateEvent;
//import com.kynsof.share.core.domain.kafka.event.EventType;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ProducerUpdateResourceEventService {
//    private final KafkaTemplate<String, String> producer;
//
//    public ProducerUpdateResourceEventService(KafkaTemplate<String, String> producer) {
//        this.producer = producer;
//    }
//
//    @Async
//    public void update(UpdateResourceKafka entity) {
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            String json = objectMapper.writeValueAsString(new CreateEvent<>(entity, EventType.UPDATED));
//            this.producer.send("doctor-update", json);
//            System.err.println("##############################################");
//            System.err.println("##############################################");
//            System.err.println("Se emitio nuevo evento para update identification.");
//            System.err.println("##############################################");
//            System.err.println("##############################################");
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ProducerUpdateResourceEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}