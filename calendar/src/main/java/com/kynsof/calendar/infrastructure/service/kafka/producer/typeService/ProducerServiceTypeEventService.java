//package com.kynsof.calendar.infrastructure.service.kafka.producer.typeService;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.share.core.domain.kafka.entity.ServiceTypeKafka;
//import com.kynsof.share.core.domain.kafka.event.CreateEvent;
//import com.kynsof.share.core.domain.kafka.event.EventType;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ProducerServiceTypeEventService {
//
//    private final KafkaTemplate<String, String> producer;
//
//    public ProducerServiceTypeEventService(KafkaTemplate<String, String> producer) {
//        this.producer = producer;
//    }
//
//    public void create(ServiceTypeKafka entity) {
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            String json = objectMapper.writeValueAsString(new CreateEvent<>(entity, EventType.CREATED));
//            this.producer.send("medic_service_type", json);
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ProducerServiceTypeEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
