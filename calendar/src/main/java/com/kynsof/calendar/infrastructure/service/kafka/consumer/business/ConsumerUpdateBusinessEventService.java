//package com.kynsof.calendar.infrastructure.service.kafka.consumer.business;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.calendar.domain.dto.BusinessDto;
//import com.kynsof.calendar.domain.service.IBusinessService;
//import com.kynsof.share.core.domain.kafka.entity.UpdateBusinessKafka;
//import com.kynsof.share.core.domain.kafka.event.EventType;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ConsumerUpdateBusinessEventService {
//
//    @Autowired
//    private IBusinessService service;
//
//    @KafkaListener(topics = "update-busines", groupId = "update-busines-calendar")
//    public void consumer(String event) {
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(event);
//
//            UpdateBusinessKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), UpdateBusinessKafka.class);
//            EventType eventType = objectMapper.treeToValue(rootNode.get("type"), EventType.class);
//            this.service.update(new BusinessDto(eventRead.getId(), eventRead.getName(), eventRead.getLatitude(),
//                    eventRead.getLongitude(), eventRead.getAddress(), eventRead.getLogo()));
//        } catch (Exception ex) {
//            Logger.getLogger(ConsumerUpdateBusinessEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
