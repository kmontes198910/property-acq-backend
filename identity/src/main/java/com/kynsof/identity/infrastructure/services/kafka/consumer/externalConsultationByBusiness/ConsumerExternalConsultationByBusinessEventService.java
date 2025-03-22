//package com.kynsof.identity.infrastructure.services.kafka.consumer.externalConsultationByBusiness;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.share.core.domain.kafka.entity.ExternalConsultationByBusinessKafka;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ConsumerExternalConsultationByBusinessEventService {
//
//    @KafkaListener(topics = "external-consultation-by-business", groupId = "identity-external-consultation-by-business")
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
//            ExternalConsultationByBusinessKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), ExternalConsultationByBusinessKafka.class);
//            System.err.println("#######################################################");
//            System.err.println("#######################################################");
//            System.err.println("Business: " + eventRead.getExternalConsultationByBusinesses().get(0).getBusinessID());
//            System.err.println("Cantidad: " + eventRead.getExternalConsultationByBusinesses().get(0).getCantExternalConsultation());
//            System.err.println("#######################################################");
//            System.err.println("#######################################################");
//        } catch (JsonProcessingException ex) {
//            System.err.println("########################");
//            System.err.println("ERROR: " + ex.getMessage());
//            Logger.getLogger(ConsumerExternalConsultationByBusinessEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
