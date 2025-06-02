package com.kynsoft.rrhh.infrastructure.services.rabbitMQ.eventPublisher;

import com.kynsoft.rrhh.infrastructure.services.rabbitMQ.Dto.AssistantRabbitMqDto;
import com.kynsoft.rrhh.infrastructure.services.rabbitMQ.RabbitMQAssistantConfig;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EventAssistantPublisherService {

    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange assistantExchange;

    @Autowired
    public EventAssistantPublisherService(RabbitTemplate rabbitTemplate,
                                 @Qualifier("assistantExchange") TopicExchange assistantExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.assistantExchange = assistantExchange;
    }

    public void publishEvent(AssistantRabbitMqDto event) {
        rabbitTemplate.convertAndSend(
                assistantExchange.getName(),
                RabbitMQAssistantConfig.ASSISTANT_CREATED_ROUTING_KEY,
                event
        );
    }
}
