//package com.kynsof.patients.infrastructure.services.kafka.producer;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.patients.domain.dto.ContactInfoDto;
//import com.kynsof.share.core.domain.kafka.entity.ContactInfoKafka;
//import com.kynsof.share.core.domain.kafka.event.CreateEvent;
//import com.kynsof.share.core.domain.kafka.event.EventType;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ProducerCreateContactEventService {
//    private final KafkaTemplate<String, String> producer;
//
//    public ProducerCreateContactEventService(KafkaTemplate<String, String> producer) {
//        this.producer = producer;
//    }
//
//    public void create(ContactInfoDto contactInfoDto) {
//
//        try {
//
//            ContactInfoKafka event = new ContactInfoKafka(
//                    contactInfoDto.getPatient().getId(),
//                    contactInfoDto.getEmail(),
//                    contactInfoDto.getTelephone(),
//                    contactInfoDto.getAddress(),
//                    contactInfoDto.getBirthdayDate().toString()
//            );
//            ObjectMapper objectMapper = new ObjectMapper();
//            String json = objectMapper.writeValueAsString(new CreateEvent<>(event, EventType.CREATED));
//
//            this.producer.send("create-contact", json);
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ProducerCreateContactEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}