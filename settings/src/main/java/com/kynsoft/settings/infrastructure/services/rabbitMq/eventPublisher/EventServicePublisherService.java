package com.kynsoft.settings.infrastructure.services.rabbitMq.eventPublisher;


import com.kynsoft.settings.domain.dto.RecoveryBed;
import com.kynsoft.settings.domain.dto.ServiceDto;
import com.kynsoft.settings.infrastructure.services.rabbitMq.RabbitMQRecoveryConfig;
import com.kynsoft.settings.infrastructure.services.rabbitMq.RabbitMQServiceConfig;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EventServicePublisherService {
    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange serviceExchange;

    @Autowired
    public EventServicePublisherService(RabbitTemplate rabbitTemplate,
                                            @Qualifier("serviceExchange")TopicExchange serviceExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.serviceExchange = serviceExchange;
    }

    public void publishServiceEvent(ServiceDto event) {
        rabbitTemplate.convertAndSend(
                serviceExchange.getName(),
                RabbitMQServiceConfig.SERVICE_CREATED_ROUTING_KEY,
                event
        );
    }
}
