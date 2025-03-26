//package com.kynsof.treatments.infrastructure.service.kafka.consumer.user;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.share.core.domain.EUserType;
//import com.kynsof.share.core.domain.kafka.entity.UserSystemKafka;
//import com.kynsof.share.core.domain.kafka.event.EventType;
//import com.kynsof.treatments.domain.dto.DoctorDto;
//import com.kynsof.treatments.domain.dto.enumDto.Status;
//import com.kynsof.treatments.domain.service.IDoctorService;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ConsumerUserSystemEventService {
//    private final IDoctorService service;
//
//    public ConsumerUserSystemEventService(IDoctorService service) {
//        this.service = service;
//    }
//
//    // Ejemplo de un método listener
//    @KafkaListener(topics = "user-system", groupId = "user-system-treatments")
//    public void listen(String event) {
//        try {
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(event);
//
//            UserSystemKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), UserSystemKafka.class);
//            EventType eventType = objectMapper.treeToValue(rootNode.get("type"), EventType.class);
//
//            if (eventType.equals(EventType.CREATED) && eventRead != null && eventRead.getUserType().equals(EUserType.DOCTORS)) {
//                this.service.create(new DoctorDto(eventRead.getId(), eventRead.getName(), eventRead.getLastName(),
//                       "", eventRead.getIdImage(), Status.ACTIVE));
//            }
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ConsumerUserSystemEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
