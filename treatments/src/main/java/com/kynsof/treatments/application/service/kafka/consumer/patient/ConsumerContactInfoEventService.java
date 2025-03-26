//package com.kynsof.treatments.infrastructure.service.kafka.consumer.patient;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.share.core.domain.kafka.entity.ContactInfoKafka;
//import com.kynsof.share.core.domain.kafka.event.EventType;
//import com.kynsof.treatments.domain.dto.PatientDto;
//import com.kynsof.treatments.domain.service.IPatientsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ConsumerContactInfoEventService {
//
//    @Autowired
//    private IPatientsService service;
//
//    @KafkaListener(topics = "create-contact", groupId = "treatments-create-contact")
//    public void listen(String event) {
//        try {
//            System.err.println("#######################################################");
//            System.err.println("#######################################################");
//            System.err.println("ENTRO");
//            System.err.println("#######################################################");
//            System.err.println("#######################################################");
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(event);
//
//            ContactInfoKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), ContactInfoKafka.class);
//            EventType eventType = objectMapper.treeToValue(rootNode.get("type"), EventType.class);
//
//            if (eventType.equals(EventType.CREATED)) {
//                try {
//                    PatientDto update = this.service.findById(eventRead.getIdPatient());
//                    update.setBirthDate(LocalDate.parse(eventRead.getBirthdayDate()));
//                    this.service.update(update);
//                } catch (Exception e) {
//                    Logger.getLogger(ConsumerContactInfoEventService.class.getName()).log(Level.SEVERE, null, e);
//                    System.err.println("#######################################################");
//                    System.err.println("#######################################################");
//                    System.err.println("No se encontro Paciente");
//                    System.err.println("#######################################################");
//                    System.err.println("#######################################################");
//                }
//
//            }
//
//        } catch (JsonProcessingException ex) {
//            System.err.println("########################");
//            System.err.println("ERROR: " + ex.getMessage());
//            Logger.getLogger(ConsumerContactInfoEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
