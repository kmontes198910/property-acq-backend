//package com.kynsoft.rrhh.infrastructure.services.kafka.consumer;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.share.core.domain.kafka.entity.BusinessKafka;
//import com.kynsof.share.core.domain.kafka.event.EventType;
//import com.kynsoft.rrhh.domain.dto.BusinessDto;
//import com.kynsoft.rrhh.domain.interfaces.services.IBusinessService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ConsumerBusinessEventService {
//
//    @Autowired
//    private IBusinessService service;
//
//    @KafkaListener(topics = "busines", groupId = "busines-rrhh")
//    public void consumer(String event) {
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(event);
//
//            BusinessKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), BusinessKafka.class);
//            EventType eventType = objectMapper.treeToValue(rootNode.get("type"), EventType.class);
//
//            if (eventType.equals(EventType.CREATED)) {
//                this.service.create(new BusinessDto(eventRead.getId(), eventRead.getName()));
//            }
//            if (eventType.equals(EventType.DELETED)) {
//                //Definir accion
//            }
//            if (eventType.equals(EventType.UPDATED)) {
//                //Definir accion
//                this.service.update(new BusinessDto(eventRead.getId(), eventRead.getName()));
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(ConsumerBusinessEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
