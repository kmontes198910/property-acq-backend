//package com.kynsof.treatments.infrastructure.service.kafka.procedure.externalConsultationByBusiness;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.share.core.domain.kafka.entity.ExternalConsultationByBusiness;
//import com.kynsof.share.core.domain.kafka.entity.ExternalConsultationByBusinessKafka;
//import com.kynsof.share.core.domain.kafka.event.CreateEvent;
//import com.kynsof.share.core.domain.kafka.event.EventType;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ProducerExternalConsultationByBusinessEventService {
//
//    private final KafkaTemplate<String, String> producer;
//
//    public ProducerExternalConsultationByBusinessEventService(KafkaTemplate<String, String> producer) {
//        this.producer = producer;
//    }
//
//    public void create(List<ExternalConsultationByBusiness> ecbbks) {
//        try {
//            ExternalConsultationByBusinessKafka event = new ExternalConsultationByBusinessKafka(ecbbks);
//            ObjectMapper objectMapper = new ObjectMapper();
//            String json = objectMapper.writeValueAsString(new CreateEvent<>(event, EventType.CREATED));
//            this.producer.send("external-consultation-by-business", json);
//
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ProducerExternalConsultationByBusinessEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
