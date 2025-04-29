package com.kynsoft.cirugia.infrastructure.services.rabbitMQ.consumer.impl;

import com.kynsoft.cirugia.domain.dto.BusinessDto;
import com.kynsoft.cirugia.domain.service.IBusinessService;
import com.kynsoft.cirugia.infrastructure.services.rabbitMQ.dto.BusinessRabbitMQDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerBusinessService {

    private final IBusinessService businessService;

    public EventConsumerBusinessService(IBusinessService businessService) {
        this.businessService = businessService;
    }

    @RabbitListener(queues = "business.queue.cirugia")
    public void handleCompanyEvent(BusinessRabbitMQDto event) {
        System.err.println(event.toString());
        this.businessService.create(new BusinessDto(
                event.getId(), 
                event.getName(), 
                event.getLatitude(), 
                event.getLongitude(), 
                event.getAddress(), 
                event.getLogo(), 
                event.getPhone(), 
                event.getEmail(), 
                event.getRuc()
        ));
    }
}
