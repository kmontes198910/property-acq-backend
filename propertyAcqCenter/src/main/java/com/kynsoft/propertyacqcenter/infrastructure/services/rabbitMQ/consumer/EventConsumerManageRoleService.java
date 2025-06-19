package com.kynsoft.propertyacqcenter.infrastructure.services.rabbitMQ.consumer;

import com.kynsoft.propertyacqcenter.domain.dto.ManageRolDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.RoleNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IManageRoleService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerManageRoleService {

    private final IManageRoleService service;

    public EventConsumerManageRoleService(IManageRoleService service) {
        this.service = service;
    }

    @RabbitListener(queues = "manage.role.queue.property.acq.center")
    public void handleCompanyEvent(ManageRolDto event) {
        try {
            service.findById(event.getId()); // Lanza excepción si no existe
            service.update(event);
        } catch (RoleNotFoundException e) {
            service.create(event);
        }
    }
}