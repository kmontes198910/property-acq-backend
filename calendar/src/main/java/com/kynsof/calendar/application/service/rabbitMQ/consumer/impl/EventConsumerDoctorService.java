package com.kynsof.calendar.application.service.rabbitMQ.consumer.impl;

import com.kynsof.calendar.application.service.rabbitMQ.dto.DoctorRabbitMqDto;
import com.kynsof.calendar.domain.dto.ResourceDto;
import com.kynsof.calendar.domain.dto.enumType.EResourceStatus;
import com.kynsof.calendar.domain.service.IResourceService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerDoctorService {

    private final IResourceService resourceService;

    public EventConsumerDoctorService(IResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @RabbitListener(queues = "doctor.queue.calendar")
    public void handleCompanyEvent(DoctorRabbitMqDto event) {
        this.resourceService.create(new ResourceDto(event.getId(), event.getName() + " " + event.getLastName(), event.getImage(), EResourceStatus.valueOf(event.getStatus())));
    }
}
