package com.kynsof.treatments.application.service.rabbitMQ.consumer.impl;

import com.kynsof.treatments.application.service.rabbitMQ.business.BusinessRabbitMQDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerBusinessService {

    @RabbitListener(queues = "${rabbitmq.queues.business}")
    public void handleCompanyEvent(BusinessRabbitMQDto event) {
        System.err.println("#################################");
        System.err.println("#################################");
        System.err.println("Consume: " + event.getName());
        System.err.println("Consume: " + event.getName());
        System.err.println("Consume: " + event.getName());
        System.err.println("#################################");
        System.err.println("#################################");
    }
}
