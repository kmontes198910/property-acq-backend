package com.kynsof.treatments.application.service.rabbitMQ.consumer.impl;

import com.kynsof.treatments.application.service.rabbitMQ.Dto.BusinessRabbitMQDto;
import com.kynsof.treatments.domain.dto.BusinessDto;
import com.kynsof.treatments.domain.service.IBusiness;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerBusinessService {

    private final IBusiness business;

    public EventConsumerBusinessService(IBusiness business) {
        this.business = business;
    }

    @RabbitListener(queues = "business.queue.treatments")
    public void handleCompanyEvent(BusinessRabbitMQDto event) {
        this.business.create(new BusinessDto(event.getId(), event.getName(), event.getLogo()));
    }
}
