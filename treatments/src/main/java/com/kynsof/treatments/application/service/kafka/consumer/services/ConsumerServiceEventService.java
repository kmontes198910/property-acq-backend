//package com.kynsof.treatments.infrastructure.service.kafka.consumer.services;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.share.core.domain.kafka.entity.Servicekafka;
//import com.kynsof.share.core.infrastructure.bus.IMediator;
//import com.kynsof.treatments.application.command.services.replicate.create.CreateServiceReplicateCommand;
//import com.kynsof.treatments.domain.dto.enumDto.EServiceStatus;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ConsumerServiceEventService {
//
//    private final IMediator mediator;
//
//    public ConsumerServiceEventService(IMediator mediator) {
//        this.mediator = mediator;
//    }
//
//    @KafkaListener(topics = "medic_service", groupId = "treatments")
//    public void listen(String event) {
//        try {
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(event);
//            Servicekafka eventRead = objectMapper.treeToValue(rootNode.get("data"), Servicekafka.class);
//            this.mediator.send(new CreateServiceReplicateCommand(
//                    eventRead.getId(),
//                    eventRead.getName(),
//                    eventRead.getPicture(),
//                    eventRead.getDescription(),
//                    eventRead.getType(),
//                    EServiceStatus.ACTIVE,
//                    eventRead.getCode()
//            ));
//
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ConsumerServiceEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
