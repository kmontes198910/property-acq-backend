package com.kynsoft.rrhh.infrastructure.services.rabbitMQ.eventPublisher;

import com.kynsoft.rrhh.infrastructure.services.rabbitMQ.Dto.NurseRabbitMqDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EventNursePublisherService {

    private final AmqpTemplate amqpTemplate;
    private final String nurseExchange;

    public EventNursePublisherService(AmqpTemplate amqpTemplate,
                                      @Value("rabbit.exchange.nurse") String nurseExchange) {
        this.amqpTemplate = amqpTemplate;
        this.nurseExchange = nurseExchange;
    }

    public void publishEvent(NurseRabbitMqDto event) {
        amqpTemplate.convertAndSend(nurseExchange, "", event);
    }
}