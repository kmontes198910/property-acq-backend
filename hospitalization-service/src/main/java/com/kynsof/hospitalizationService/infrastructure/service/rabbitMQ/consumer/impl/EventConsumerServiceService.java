package com.kynsof.hospitalizationService.infrastructure.service.rabbitMQ.consumer.impl;


import com.kynsof.hospitalizationService.domain.dto.ServiceDto;
import com.kynsof.hospitalizationService.domain.service.IServiceService;
import com.kynsof.hospitalizationService.infrastructure.repositories.query.ServiceReadDataJPARepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventConsumerServiceService {
    private final IServiceService service;
    private final ServiceReadDataJPARepository query;

    public EventConsumerServiceService(IServiceService service, ServiceReadDataJPARepository query) {
        this.service = service;
        this.query = query;
    }
    @RabbitListener(queues = "service.queue.hospitalization")
    public void handleCompanyEvent(ServiceDto event) {
        boolean isService = query.findById(event.getId()).isPresent();
        if(isService){
            this.service.update(event);
        }else{
            this.service.create(event);
        }

    }
}