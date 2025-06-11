package com.kynsof.hospitalizationService.infrastructure.service.rabbitMQ.consumer.impl;


import com.kynsof.hospitalizationService.domain.dto.ServiceTypeDto;
import com.kynsof.hospitalizationService.domain.service.IServiceTypeService;
import com.kynsof.hospitalizationService.infrastructure.repositories.query.ServiceTypeReadDataJPARepository;
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
    @RabbitListener(queues = "service-type.queue.hospitalization")
    public void handleCompanyEvent(ServiceTypeDto event) {
        boolean isService = query.findById(event.getId()).isPresent();
        log.info("Reciviendo el evento:"+ event.getId());
        if(isService){
            this.service.update(event);
        }else{
            this.service.create(event);
        }

    }
}

