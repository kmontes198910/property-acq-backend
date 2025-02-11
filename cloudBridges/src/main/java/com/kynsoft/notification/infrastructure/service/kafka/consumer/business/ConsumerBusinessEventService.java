package com.kynsoft.notification.infrastructure.service.kafka.consumer.business;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.share.core.domain.kafka.entity.BusinessKafka;
import com.kynsoft.notification.domain.dto.TenantDto;
import com.kynsoft.notification.domain.service.ITenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ConsumerBusinessEventService {

    @Autowired
    private ITenantService service;

    @KafkaListener(topics = "busines", groupId = "busines-cloud-bridges")
    public void consumer(String event) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(event);

            BusinessKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), BusinessKafka.class);

            this.service.create(new TenantDto(UUID.randomUUID(), eventRead.getId().toString(), null));

        } catch (Exception ex) {
            Logger.getLogger(ConsumerBusinessEventService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
