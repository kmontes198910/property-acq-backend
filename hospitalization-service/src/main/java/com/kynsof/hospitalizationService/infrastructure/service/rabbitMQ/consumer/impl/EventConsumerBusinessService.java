package com.kynsof.hospitalizationService.infrastructure.service.rabbitMQ.consumer.impl;


import com.kynsof.hospitalizationService.domain.dto.BusinessDto;
import com.kynsof.hospitalizationService.domain.service.IBusinessService;
import com.kynsof.hospitalizationService.infrastructure.service.rabbitMQ.consumer.dto.BusinessRabbitMQDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerBusinessService {

    private final IBusinessService businessService;

    public EventConsumerBusinessService(IBusinessService businessService) {
        this.businessService = businessService;
    }

    @RabbitListener(queues = "business.queue.hospitalization")
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