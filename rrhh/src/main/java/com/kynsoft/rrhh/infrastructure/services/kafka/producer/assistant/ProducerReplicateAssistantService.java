//package com.kynsoft.rrhh.infrastructure.services.kafka.producer.assistant;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.share.core.domain.kafka.entity.DoctorKafka;
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
//public class ProducerReplicateAssistantService {
//
//    private final KafkaTemplate<String, String> producer;
//
//    public ProducerReplicateAssistantService(KafkaTemplate<String, String> producer) {
//        this.producer = producer;
//    }
//
//    @Async
//    public void create(DoctorKafka entity) {
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            String json = objectMapper.writeValueAsString(new CreateEvent<>(entity, EventType.CREATED));
//            this.producer.send("medinec-replicate-assistant", json);
//        } catch (Exception ex) {
//            Logger.getLogger(ProducerReplicateAssistantService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
