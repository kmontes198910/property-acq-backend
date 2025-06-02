package com.kynsoft.cirugia.infrastructure.services.rabbitMQ.consumer.impl;

import com.kynsoft.cirugia.domain.dto.DoctorDto;
import com.kynsoft.cirugia.domain.service.IDoctorService;
import com.kynsoft.cirugia.infrastructure.services.rabbitMQ.dto.DoctorRabbitMqDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerDoctorService {

    private final IDoctorService resourceService;

    public EventConsumerDoctorService(IDoctorService resourceService) {
        this.resourceService = resourceService;
    }

    @RabbitListener(queues = "doctor.queue.cirugia")
    public void handleCompanyEvent(DoctorRabbitMqDto event) {
        this.resourceService.create(new DoctorDto(
                event.getId(), 
                event.getName(), 
                event.getLastName(), 
                event.getIdentification(),
                event.getRegisterNumber()
        ));
    }
}
