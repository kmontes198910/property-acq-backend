//package com.kynsof.calendar.infrastructure.service.kafka.producer;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.share.core.domain.kafka.entity.GenerateReportKafka;
//import com.kynsof.share.core.domain.kafka.event.CreateEvent;
//import com.kynsof.share.core.domain.kafka.event.EventType;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ProducerGenerateReportEventService {
//
//    private final KafkaTemplate<String, String> producer;
//
//    public ProducerGenerateReportEventService(KafkaTemplate<String, String> producer) {
//        this.producer = producer;
//    }
//
//    public void create(GenerateReportKafka entity) {
//
//        try {
//            System.err.println("#######################################################");
//            System.err.println("#######################################################");
//            System.err.println("#######################################################");
//            System.err.println("#######################################################");
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            String json = objectMapper.writeValueAsString(new CreateEvent<>(entity, EventType.CREATED));
//            this.producer.send("generate-report", json);
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ProducerGenerateReportEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
