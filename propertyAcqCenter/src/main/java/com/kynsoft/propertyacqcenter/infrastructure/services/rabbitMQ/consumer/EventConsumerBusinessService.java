package com.kynsoft.propertyacqcenter.infrastructure.services.rabbitMQ.consumer;

import com.kynsoft.propertyacqcenter.infrastructure.services.rabbitMQ.dto.BusinessRabbitMQDto;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.propertyacqcenter.domain.services.IBusinessService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerBusinessService {

    private final IBusinessService business;

    public EventConsumerBusinessService(IBusinessService business) {
        this.business = business;
    }

    @RabbitListener(queues = "business.queue.property.acq.center")
    public void handleCompanyEvent(BusinessRabbitMQDto event) {
        this.business.create(new BusinessDto(event.getId(), event.getName()));
    }
}
