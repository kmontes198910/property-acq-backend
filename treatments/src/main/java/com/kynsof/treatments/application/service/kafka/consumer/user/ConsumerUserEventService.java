//package com.kynsof.treatments.infrastructure.service.kafka.consumer.user;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.share.core.domain.kafka.entity.UserKafka;
//import com.kynsof.share.core.domain.kafka.event.EventType;
//import com.kynsof.treatments.domain.dto.PatientDto;
//import com.kynsof.treatments.domain.dto.enumDto.Status;
//import com.kynsof.treatments.domain.service.IPatientsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ConsumerUserEventService {
//    @Autowired
//    private IPatientsService service;
//
//    // Ejemplo de un método listener
//    @KafkaListener(topics = "user", groupId = "user-treatments")
//    public void listen(String event) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(event);
//
//            UserKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), UserKafka.class);
//            EventType eventType = objectMapper.treeToValue(rootNode.get("type"), EventType.class);
//            if (eventType.equals(EventType.CREATED)) {
//                //Definir accion
//                this.service.create(new PatientDto(UUID.fromString(eventRead.getId()), eventRead.getIdentification(), eventRead.getFirstname(), eventRead.getLastname(), eventRead.getGender(), Status.ACTIVE, null));
//
//            }
//            if (eventType.equals(EventType.DELETED)) {
//                //Definir accion
//                System.err.println("#######################################################");
//                System.err.println("#######################################################");
//                System.err.println("SE EJECUTA UN DELETED");
//                System.err.println("#######################################################");
//                System.err.println("#######################################################");
//
//            }
//            if (eventType.equals(EventType.UPDATED)) {
//                //Definir accion
//                System.err.println("#######################################################");
//                System.err.println("#######################################################");
//                System.err.println("SE EJECUTA UN UPDATE");
//                System.err.println("#######################################################");
//                System.err.println("#######################################################");
//                this.service.update(new PatientDto(UUID.fromString(eventRead.getId()), eventRead.getIdentification(), eventRead.getFirstname(), eventRead.getLastname(), eventRead.getGender(), Status.ACTIVE, null));
//            }
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ConsumerUserEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
