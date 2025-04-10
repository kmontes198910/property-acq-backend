package com.kynsof.identity.infrastructure.services.rabbitMq.eventPublisher;

import com.kynsof.identity.infrastructure.services.rabbitMq.dto.BusinessRabbitMQDto;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EventPublisherService {

    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange businessExchange;

    @Value("${rabbitmq.routing.key.business}")
    public String BUSINESS_CREATED_ROUTING_KEY;

    @Autowired
    public EventPublisherService(RabbitTemplate rabbitTemplate,
                                 @Qualifier("businessExchange") TopicExchange businessExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.businessExchange = businessExchange;
    }

    public void publishBusinessEvent(BusinessRabbitMQDto event) {
        rabbitTemplate.convertAndSend(
                businessExchange.getName(),
                BUSINESS_CREATED_ROUTING_KEY,
                event
        );
    }
}
