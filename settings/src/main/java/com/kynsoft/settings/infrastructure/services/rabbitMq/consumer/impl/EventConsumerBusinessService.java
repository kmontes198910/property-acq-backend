package com.kynsoft.settings.infrastructure.services.rabbitMq.consumer.impl;


import com.kynsoft.settings.domain.dto.BusinessDto;
import com.kynsoft.settings.domain.services.IBusinessService;
import com.kynsoft.settings.infrastructure.services.rabbitMq.consumer.dto.BusinessRabbitMQDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerBusinessService {

    private final IBusinessService businessService;

    public EventConsumerBusinessService(IBusinessService businessService) {
        this.businessService = businessService;
    }

    @RabbitListener(queues = "business.queue.settings")
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