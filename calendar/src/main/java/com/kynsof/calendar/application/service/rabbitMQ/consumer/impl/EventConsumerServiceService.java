package com.kynsof.calendar.application.service.rabbitMQ.consumer.impl;


import com.kynsof.calendar.domain.dto.ServiceDto;
import com.kynsof.calendar.domain.service.IServiceService;
import com.kynsof.calendar.infrastructure.repository.query.ServiceReadDataJPARepository;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventConsumerServiceService {

    private final IServiceService service;

    public EventConsumerServiceService(IServiceService service) {
        this.service = service;
    }

    @RabbitListener(queues = "service.queue.calendar")
    public void handleCompanyEvent(ServiceDto event) {
        try {
            service.findByIds(event.getId());
            service.update(event);
        } catch (BusinessNotFoundException e) {
            service.create(event);
        }
    }
}