package com.kynsoft.cirugia.infrastructure.services.rabbitMQ.consumer.impl;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsoft.cirugia.domain.dto.ServiceDto;
import com.kynsoft.cirugia.domain.service.IServiceService;
import com.kynsoft.cirugia.infrastructure.repository.query.ServiceReadDataJPARepository;
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

    @RabbitListener(queues = "service.queue.cirugia")
    public void handleCompanyEvent(ServiceDto event) {
        try {
            service.findByIds(event.getId()); // valida existencia
            service.update(event);
        } catch (BusinessNotFoundException e) {
            service.create(event);
        }
    }
}