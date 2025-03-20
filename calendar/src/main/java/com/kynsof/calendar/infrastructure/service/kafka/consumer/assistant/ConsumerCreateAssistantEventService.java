//package com.kynsof.calendar.infrastructure.service.kafka.consumer.assistant;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kynsof.calendar.domain.dto.ResourceDto;
//import com.kynsof.calendar.domain.dto.enumType.EResourceStatus;
//import com.kynsof.calendar.domain.service.IResourceService;
//import com.kynsof.share.core.domain.kafka.entity.DoctorKafka;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.UUID;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class ConsumerCreateAssistantEventService {
//    private final IResourceService service;
//    private final IResourceService resourceService;
//
//    public ConsumerCreateAssistantEventService(IResourceService service, IResourceService resourceService) {
//
//
//        this.service = service;
//        this.resourceService = resourceService;
//    }
//
//    @KafkaListener(topics = "medinec-replicate-assistant", groupId = "calendar-assistant")
//    public void listen(String event) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(event);
//            DoctorKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), DoctorKafka.class);
//
//            this.service.create(new ResourceDto(
//                    eventRead.getId(), eventRead.getName() + " " + eventRead.getLastName(),
//                    eventRead.getImage(), EResourceStatus.ACTIVE
//            ));
//
//            try {
//                if (eventRead.getBusiness() != null) {
//                    resourceService.addBusiness(UUID.fromString(eventRead.getBusiness()), eventRead.getId(), LocalDate.now());
//                }
//
//            } catch (Exception e) {
//            }
//
//        } catch (Exception ex) {
//            Logger.getLogger(ConsumerCreateAssistantEventService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
