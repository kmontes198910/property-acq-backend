//package com.kynsof.treatments.infrastructure.service.kafka.consumer.doctor;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.share.core.domain.kafka.entity.UpdateResourceKafka;
//import com.kynsof.share.core.domain.kafka.event.EventType;
//import com.kynsof.treatments.domain.dto.DoctorDto;
//import com.kynsof.treatments.domain.service.IDoctorService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ConsumerUpdateResourceEventService {
//
//    @Autowired
//    private IDoctorService service;
//
//    @KafkaListener(topics = "doctor-update", groupId = "user-treatments")
//    public void listen(String event) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(event);
//            UpdateResourceKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), UpdateResourceKafka.class);
//            EventType eventType = objectMapper.treeToValue(rootNode.get("type"), EventType.class);
//            DoctorDto doctorDto = this.service.findById(UUID.fromString(eventRead.getId()));
//            this.service.update(doctorDto);
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ConsumerUpdateResourceEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
