package com.kynsoft.propertyacqcenter.infrastructure.services.rabbitMQ.eventPublisher;

import com.kynsoft.propertyacqcenter.infrastructure.services.rabbitMQ.RabbitMQEmployeeConfig;
import com.kynsoft.propertyacqcenter.infrastructure.services.rabbitMQ.dto.RabbitMqEmployeeDto;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class EventEmployeePublisherService {
    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange recoveryExchange;

    @Autowired
    public EventEmployeePublisherService(RabbitTemplate rabbitTemplate,
                                            @Qualifier("employeeExchangeBed")TopicExchange recoveryExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.recoveryExchange = recoveryExchange;
    }

    public void publishRecoveryBedEvent(RabbitMqEmployeeDto event) {
        rabbitTemplate.convertAndSend(
                recoveryExchange.getName(),
                RabbitMQEmployeeConfig.EMPLOYEE_CREATED_ROUTING_KEY,
                event
        );
    }
}
