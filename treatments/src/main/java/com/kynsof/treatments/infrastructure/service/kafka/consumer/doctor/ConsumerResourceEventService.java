//package com.kynsof.treatments.infrastructure.service.kafka.consumer.doctor;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.share.core.domain.kafka.entity.UserSystemKafka;
//import com.kynsof.share.core.domain.kafka.event.EventType;
//import com.kynsof.treatments.domain.dto.DoctorDto;
//import com.kynsof.treatments.domain.service.IDoctorService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ConsumerResourceEventService {
//    @Autowired
//    private IDoctorService service;
//
//    // Ejemplo de un método listener
//    @KafkaListener(topics = "user-system-update", groupId = "treatments")
//    public void listen(String event) {
//        try {
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(event);
//
//            UserSystemKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), UserSystemKafka.class);
//            EventType eventType = objectMapper.treeToValue(rootNode.get("type"), EventType.class);
//            DoctorDto doctorDto = this.service.findById(eventRead.getId());
//            doctorDto.setName(eventRead.getName());
//            doctorDto.setImage(eventRead.getIdImage());
//           this.service.update(doctorDto);
//
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ConsumerResourceEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
