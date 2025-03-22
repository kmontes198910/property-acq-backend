//package com.kynsof.evaluation.infrastructure.service.kafka.consumer.create.doctor;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.share.core.domain.kafka.entity.DoctorKafka;
//import com.kynsof.evaluation.domain.dto.DoctorDto;
//import com.kynsof.evaluation.domain.dto.enumDto.Status;
//import com.kynsof.evaluation.domain.service.IDoctorService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ConsumerCreateDoctorEventService {
//    @Autowired
//    private IDoctorService service;
//
//    // Ejemplo de un método listener
//    @KafkaListener(topics = "medinec-replicate-doctor", groupId = "treatments")
//    public void listen(String event) {
//        try {
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(event);
//            DoctorKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), DoctorKafka.class);
//
//            this.service.create(new DoctorDto(
//                    eventRead.getId(),
//                    eventRead.getIdentification(),
//                    eventRead.getName() ,
//                    eventRead.getLastName(),
//                    eventRead.getRegisterNumber(),
//                    eventRead.getImage(),
//                    Status.ACTIVE
//            ));
//
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ConsumerCreateDoctorEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
