//package com.kynsof.identity.infrastructure.services.kafka.producer.firebaseToken;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.share.core.domain.kafka.entity.FirebaseTokenKafka;
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
//public class ProducerReplicateFirebaseTokenService {
//
//    private final KafkaTemplate<String, String> producer;
//
//    public ProducerReplicateFirebaseTokenService(KafkaTemplate<String, String> producer) {
//        this.producer = producer;
//    }
//
//    @Async
//    public void create(FirebaseTokenKafka entity) {
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            String json = objectMapper.writeValueAsString(new CreateEvent<>(entity, EventType.CREATED));
//            this.producer.send("medinec-replicate-firebase-token", json);
//        } catch (Exception ex) {
//            Logger.getLogger(ProducerReplicateFirebaseTokenService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
