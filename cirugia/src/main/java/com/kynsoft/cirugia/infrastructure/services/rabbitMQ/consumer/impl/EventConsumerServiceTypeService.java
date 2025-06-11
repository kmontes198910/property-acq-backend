package com.kynsoft.cirugia.infrastructure.services.rabbitMQ.consumer.impl;


import com.kynsoft.cirugia.domain.dto.ServiceTypeDto;
import com.kynsoft.cirugia.domain.service.IServiceTypeService;
import com.kynsoft.cirugia.infrastructure.repository.query.ServiceTypeReadDataJPARepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventConsumerServiceTypeService {
    private final IServiceTypeService service;
    private final ServiceTypeReadDataJPARepository query;

    public EventConsumerServiceTypeService(IServiceTypeService service, ServiceTypeReadDataJPARepository query) {
        this.service = service;
        this.query = query;
    }
    @RabbitListener(queues = "service-type.queue.cirugia")
    public void handleCompanyEvent(ServiceTypeDto event) {
        boolean isService = query.findById(event.getId()).isPresent();
        if(isService){
            this.service.update(event);
        }else{
            this.service.create(event);
        }

    }
}
