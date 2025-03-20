//package com.kynsof.payment.infrastructure.service.kafka.consumer.doctor;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.payment.domain.dto.ClientDto;
//import com.kynsof.payment.domain.service.IClientService;
//import com.kynsof.share.core.domain.kafka.entity.UserSystemKafka;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ConsumerResourceEventService {
//
//    @Autowired
//    private IClientService service;
//
//    // Ejemplo de un método listener
//    @KafkaListener(topics = "user-system-update", groupId = "payment")
//    public void listen(String event) {
//        try {
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(event);
//
//            UserSystemKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), UserSystemKafka.class);
//            ClientDto dto = this.service.findById(eventRead.getId());
//            dto.setName(eventRead.getName());
//            this.service.update(dto);
//
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ConsumerResourceEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
