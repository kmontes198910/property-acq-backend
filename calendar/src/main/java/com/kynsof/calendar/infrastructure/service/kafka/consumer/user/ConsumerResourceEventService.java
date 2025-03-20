//package com.kynsof.calendar.infrastructure.service.kafka.consumer.user;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.calendar.domain.dto.ResourceDto;
//import com.kynsof.calendar.domain.service.IResourceService;
//import com.kynsof.share.core.domain.kafka.entity.UserSystemKafka;
//import com.kynsof.share.core.domain.kafka.event.EventType;
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
//    private IResourceService service;
//
//    // Ejemplo de un método listener
//    @KafkaListener(topics = "user-system-update", groupId = "calendar")
//    public void listen(String event) {
//        try {
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(event);
//
//            UserSystemKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), UserSystemKafka.class);
//            EventType eventType = objectMapper.treeToValue(rootNode.get("type"), EventType.class);
//            ResourceDto resourceDto = this.service.findById(eventRead.getId());
//            resourceDto.setName(eventRead.getName() + " " + eventRead.getLastName());
//            resourceDto.setImage(eventRead.getIdImage());
//            this.service.update(resourceDto);
//
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ConsumerResourceEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
