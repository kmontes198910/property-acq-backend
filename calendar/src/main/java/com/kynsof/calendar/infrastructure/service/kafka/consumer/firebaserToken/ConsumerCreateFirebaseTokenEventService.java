//package com.kynsof.calendar.infrastructure.service.kafka.consumer.firebaserToken;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.calendar.domain.dto.FirebaseTokenDto;
//import com.kynsof.calendar.domain.service.IFirebaseTokenService;
//import com.kynsof.share.core.domain.kafka.entity.FirebaseTokenKafka;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ConsumerCreateFirebaseTokenEventService {
//
//    private final IFirebaseTokenService service;
//
//    public ConsumerCreateFirebaseTokenEventService(IFirebaseTokenService service) {
//        this.service = service;
//    }
//
//    @KafkaListener(topics = "medinec-replicate-firebase-token", groupId = "firebase-token")
//    public void listen(String event) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(event);
//            FirebaseTokenKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), FirebaseTokenKafka.class);
//
//            this.service.create(new FirebaseTokenDto(eventRead.getId(), eventRead.getToken()));
//
//        } catch (Exception ex) {
//            Logger.getLogger(ConsumerCreateFirebaseTokenEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
