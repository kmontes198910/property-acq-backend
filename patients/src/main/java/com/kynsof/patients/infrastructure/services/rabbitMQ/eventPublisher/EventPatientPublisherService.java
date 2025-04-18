package com.kynsof.patients.infrastructure.services.rabbitMQ.eventPublisher;

import com.kynsof.patients.infrastructure.services.rabbitMQ.config.RabbitMQPatientConfig;
import com.kynsof.patients.infrastructure.services.rabbitMQ.dto.RabbitMQPatientDto;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EventPatientPublisherService {

    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange assistantExchange;

    @Autowired
    public EventPatientPublisherService(RabbitTemplate rabbitTemplate,
                                 @Qualifier("patientExchange") TopicExchange assistantExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.assistantExchange = assistantExchange;
    }

    public void publishEvent(RabbitMQPatientDto event) {
        rabbitTemplate.convertAndSend(
                assistantExchange.getName(),
                RabbitMQPatientConfig.PATIENT_CREATED_ROUTING_KEY,
                event
        );
    }
}
