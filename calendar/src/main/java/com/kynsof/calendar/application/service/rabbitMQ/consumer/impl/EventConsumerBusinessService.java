package com.kynsof.calendar.application.service.rabbitMQ.consumer.impl;

import com.kynsof.calendar.application.service.rabbitMQ.dto.BusinessRabbitMQDto;
import com.kynsof.calendar.domain.dto.BusinessDto;
import com.kynsof.calendar.domain.service.IBusinessService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerBusinessService {

    private final IBusinessService businessService;

    public EventConsumerBusinessService(IBusinessService businessService) {
        this.businessService = businessService;
    }

    @RabbitListener(queues = "business.queue.scheduled")
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
