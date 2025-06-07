package com.kynsoft.settings.infrastructure.services.rabbitMq.eventPublisher;



import com.kynsoft.settings.domain.dto.RecoveryRoom;
import com.kynsoft.settings.infrastructure.services.rabbitMq.RabbitMQRecoveryConfig;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EventRecoveryRoomPublisherService {
    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange recoveryExchange;

    @Autowired
    public EventRecoveryRoomPublisherService(RabbitTemplate rabbitTemplate,
                                             @Qualifier("recoveryExchangeRoom") TopicExchange recoveryExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.recoveryExchange = recoveryExchange;
    }

    public void publishRecoveryRoomEvent(RecoveryRoom event) {
        rabbitTemplate.convertAndSend(
                recoveryExchange.getName(),
                RabbitMQRecoveryConfig.RECOVERY_CREATED_ROUTING_KEY,
                event
        );
    }
}
