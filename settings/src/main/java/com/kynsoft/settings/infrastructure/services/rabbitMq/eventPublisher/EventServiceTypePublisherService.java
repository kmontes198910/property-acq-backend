package com.kynsoft.settings.infrastructure.services.rabbitMq.eventPublisher;



import com.kynsoft.settings.domain.dto.ServiceTypeDto;
import com.kynsoft.settings.infrastructure.services.rabbitMq.RabbitMQServiceConfig;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EventServiceTypePublisherService {
    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange serviceTypeExchange;

    @Autowired
    public EventServiceTypePublisherService(RabbitTemplate rabbitTemplate,
                                        @Qualifier("serviceTypeExchange")TopicExchange serviceTypeExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.serviceTypeExchange = serviceTypeExchange;
    }

    public void publishServiceTypeEvent(ServiceTypeDto event) {
        System.out.println("publicando el mensaje "+ event.getId());
        rabbitTemplate.convertAndSend(
                serviceTypeExchange.getName(),
                RabbitMQServiceConfig.SERVICE_CREATED_ROUTING_KEY,
                event
        );
    }
}
