package com.kynsoft.settings.infrastructure.services.rabbitMq.eventPublisher;

import com.kynsoft.settings.domain.dto.RecoveryBed;
import com.kynsoft.settings.infrastructure.services.rabbitMq.RabbitMQRecoveryConfig;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class EventRecoveryBedPublisherService {
    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange recoveryExchange;

    @Autowired
    public EventRecoveryBedPublisherService(RabbitTemplate rabbitTemplate,
                                            @Qualifier("recoveryExchangeBed")TopicExchange recoveryExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.recoveryExchange = recoveryExchange;
    }

    public void publishRecoveryBedEvent(RecoveryBed event) {
        rabbitTemplate.convertAndSend(
                recoveryExchange.getName(),
                RabbitMQRecoveryConfig.RECOVERY_CREATED_ROUTING_KEY,
                event
        );
    }
}
