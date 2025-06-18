package com.kynsof.patients.infrastructure.services.rabbitMQ.eventPublisher;


import com.kynsof.patients.infrastructure.services.rabbitMQ.config.RabbitMQPatientConfig;
import com.kynsof.patients.infrastructure.services.rabbitMQ.dto.RabbitMQPatientDeleteDto;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EventPatientDeletePublisherService {

    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange patientDeleteExchange;

    @Autowired
    public EventPatientDeletePublisherService(RabbitTemplate rabbitTemplate,
                                        @Qualifier("patientDeleteExchange") TopicExchange patientDeleteExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.patientDeleteExchange = patientDeleteExchange;
    }

    public void publishEvent(RabbitMQPatientDeleteDto event) {
        rabbitTemplate.convertAndSend(
                patientDeleteExchange.getName(),
                RabbitMQPatientConfig.PATIENT_CREATED_ROUTING_KEY,
                event
        );
    }
}