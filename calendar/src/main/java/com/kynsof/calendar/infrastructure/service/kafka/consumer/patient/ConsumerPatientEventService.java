//package com.kynsof.calendar.infrastructure.service.kafka.consumer.patient;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.calendar.domain.dto.PatientDto;
//import com.kynsof.calendar.domain.dto.enumType.PatientStatus;
//import com.kynsof.calendar.domain.service.IPatientsService;
//import com.kynsof.share.core.domain.kafka.entity.CustomerKafka;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ConsumerPatientEventService {
//
//    private final IPatientsService service;
//    private final ObjectMapper objectMapper;
////    @Value("${kafka.topic.create-customer}")  // Inyecta el nombre del tópico desde la configuración
////    private String topic;
//
//    public ConsumerPatientEventService(IPatientsService service, ObjectMapper objectMapper) {
//        this.service = service;
//        this.objectMapper = objectMapper;
//    }
//
//    //@KafkaListener(topics = "medinec-create-patient", groupId = "calendar-patient")
//    @KafkaListener(topics = "${kafka.topic.create-customer}", groupId = "calendar-patient")
//    public void listen(String event) {
//        try {
//
//           // ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(event);
//
//            CustomerKafka eventRead = objectMapper.treeToValue(rootNode.get("data"),
//                    CustomerKafka.class);
//
//            this.service.create(new PatientDto(
//                    UUID.fromString(eventRead.getId()),
//                    eventRead.getIdentificationNumber(),
//                    eventRead.getEmail(),
//                    eventRead.getFirstName(),
//                    eventRead.getLastName(),
//                    PatientStatus.ACTIVE,
//                    eventRead.getImage(),
//                    eventRead.getProfession()
//            ));
//
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ConsumerPatientEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
