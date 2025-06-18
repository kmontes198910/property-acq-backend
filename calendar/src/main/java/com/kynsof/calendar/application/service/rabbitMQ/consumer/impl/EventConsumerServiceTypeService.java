package com.kynsof.calendar.application.service.rabbitMQ.consumer.impl;



import com.kynsof.calendar.domain.dto.ServiceTypeDto;
import com.kynsof.calendar.domain.service.IServiceTypeService;
import com.kynsof.calendar.infrastructure.repository.query.ServiceTypeReadDataJPARepository;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventConsumerServiceTypeService {
    private final IServiceTypeService service;


    public EventConsumerServiceTypeService(IServiceTypeService service) {
        this.service = service;

    }
    @RabbitListener(queues = "service-type.queue.calendar")
    public void handleCompanyEvent(ServiceTypeDto event) {
        try {
            service.findById(event.getId());
            service.update(event);
        } catch (BusinessNotFoundException e) {
            service.create(event);
        }

    }
}
