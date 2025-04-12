package com.kynsof.evaluation.infrastructure.service.rabbitMQ.consumer.impl;

import com.kynsof.evaluation.infrastructure.service.rabbitMQ.dto.BusinessRabbitMQDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerBusinessService {

    @RabbitListener(queues = "business.queue.evaluation")
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
