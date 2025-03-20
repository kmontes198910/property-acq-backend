//package com.kynsoft.rrhh.infrastructure.services.kafka.consumer;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.share.core.domain.kafka.entity.UserBusinessKafka;
//import com.kynsof.share.core.domain.kafka.event.EventType;
//import com.kynsof.share.utils.ConfigureTimeZone;
//import com.kynsoft.rrhh.domain.dto.BusinessDto;
//import com.kynsoft.rrhh.domain.dto.UserBusinessRelationDto;
//import com.kynsoft.rrhh.domain.dto.UserSystemDto;
//import com.kynsoft.rrhh.domain.interfaces.services.IBusinessService;
//import com.kynsoft.rrhh.domain.interfaces.services.IUserBusinessRelationService;
//import com.kynsoft.rrhh.domain.interfaces.services.IUserSystemService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ConsumerUserBusinessEventService {
//
//    @Autowired
//    private IUserBusinessRelationService service;
//
//    @Autowired
//    private IBusinessService businessService;
//
//    @Autowired
//    private IUserSystemService userSystemService;
//
//    // Ejemplo de un método listener
//    @KafkaListener(topics = "user-business", groupId = "rrhh-user-busines")
//    public void listen(String event) {
//        try {
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(event);
//
//            UserBusinessKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), UserBusinessKafka.class);
//            EventType eventType = objectMapper.treeToValue(rootNode.get("type"), EventType.class);
//
//            if (eventType.equals(EventType.CREATED)) {
//                //Definir accion
//                System.err.println("#######################################################");
//                System.err.println("#######################################################");
//                System.err.println("SE EJECUTA UN CREATED");
//                System.err.println("#######################################################");
//                System.err.println("#######################################################");
//
//                BusinessDto businessDto = this.businessService.findById(eventRead.getIdBusiness());
//                UserSystemDto userSystemDto = this.userSystemService.findById(eventRead.getIdUser());
//
//                this.service.create(new UserBusinessRelationDto(UUID.randomUUID(), userSystemDto, businessDto, "ACTIVE", ConfigureTimeZone.getTimeZone()));
//            }
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ConsumerUserBusinessEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
