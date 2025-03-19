package com.kynsof.payment.infrastructure.service.kafka.consumer.business;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.payment.domain.dto.BusinessDto;
import com.kynsof.payment.domain.service.IBusiness;
import com.kynsof.share.core.domain.kafka.entity.BusinessKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ConsumerBusinessEventService {

    private final IBusiness service;

    public ConsumerBusinessEventService(IBusiness service) {
        this.service = service;
    }

    @KafkaListener(topics = "busines", groupId = "busines-payment")
    public void consumer(String event) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(event);

            BusinessKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), BusinessKafka.class);
            this.service.create(new BusinessDto(eventRead.getId(), eventRead.getName(), eventRead.getLogo(), "RUC", eventRead.getPhone(), eventRead.getEmail()));
        } catch (Exception ex) {
            Logger.getLogger(ConsumerBusinessEventService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
