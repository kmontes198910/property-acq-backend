package com.kynsoft.rrhh.infrastructure.services.rabbitMQ.eventPublisher;

import com.kynsoft.rrhh.infrastructure.services.rabbitMQ.Dto.DoctorRabbitMqDto;
import com.kynsoft.rrhh.infrastructure.services.rabbitMQ.RabbitMQDoctorConfig;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EventDoctorPublisherService {

    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange doctorExchange;

    @Autowired
    public EventDoctorPublisherService(RabbitTemplate rabbitTemplate,
                                 @Qualifier("doctorExchange") TopicExchange doctorExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.doctorExchange = doctorExchange;
    }

    public void publishEvent(DoctorRabbitMqDto event) {
        rabbitTemplate.convertAndSend(
                doctorExchange.getName(),
                RabbitMQDoctorConfig.DOCTOR_CREATED_ROUTING_KEY,
                event
        );
    }
}
